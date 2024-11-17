package com.example.roomiesapplication.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomiesapplication.model.ErrorMsgConstants
import com.example.roomiesapplication.model.UsernameEntryData
import com.example.roomiesapplication.model.ValidationConstants
import com.example.roomiesapplication.viewmodels.states.EditProfileScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EditProfileScreenViewModel: ViewModel() {

    private val _editProfileScreenState = mutableStateOf((EditProfileScreenState()))
    val editProfileScreenState: State<EditProfileScreenState> = _editProfileScreenState

    init {
        viewModelScope.launch {
            fetchUserData()
        }
    }

    private suspend fun fetchUserData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val db = FirebaseFirestore.getInstance()
        try {
            val docSnapshot = db.collection("users").document(uid).get().await()
            _editProfileScreenState.value = _editProfileScreenState.value.copy(
                Username = docSnapshot.getString("username").orEmpty(),
                Fullname = docSnapshot.getString("fullname").orEmpty(),
                ProfileDescription = docSnapshot.getString("profileDescription").orEmpty(),
                Uid = uid,
                PreviousUsername = docSnapshot.getString("username").orEmpty()
            )
        } catch (error: Exception) {
            _editProfileScreenState.value.EditProfileErrorMsg = error.toString()
        }
    }

    suspend fun UpdateUserEntry(): Boolean {
        return try {
            val usernameExists = checkForExistingUsername()
            if(_editProfileScreenState.value.UsernameValid && _editProfileScreenState.value.FullnameValid && _editProfileScreenState.value.ProfileDescriptionValid && !usernameExists) {
                val db = FirebaseFirestore.getInstance()
                db.collection("users").document(_editProfileScreenState.value.Uid).update("username", _editProfileScreenState.value.Username)
                db.collection("usernames").document(_editProfileScreenState.value.PreviousUsername).delete()
                val newUsernameEntry = UsernameEntryData(
                    uid = _editProfileScreenState.value.Uid
                )
                db.collection("usernames").document(_editProfileScreenState.value.Username).set(newUsernameEntry)
                db.collection("users").document(_editProfileScreenState.value.Uid).update("fullname", _editProfileScreenState.value.Fullname)
                db.collection("users").document(_editProfileScreenState.value.Uid).update("profileDescription", _editProfileScreenState.value.ProfileDescription)
                db.collection("users").document(_editProfileScreenState.value.Uid).update("acceptingStatus", _editProfileScreenState.value.IsAcceptingRoommates)
                _editProfileScreenState.value.EditProfileErrorMsg = ErrorMsgConstants.ON_UPDATE_SUCCESS
                true
            } else {
                _editProfileScreenState.value.EditProfileErrorMsg = ErrorMsgConstants.ON_UPDATE_FAILURE
                false
            }
        } catch (error: Exception) {
            _editProfileScreenState.value.EditProfileErrorMsg = error.toString()
            false
        }

    }

    fun OnAcceptingStatusChange(change: Boolean) {
        _editProfileScreenState.value = editProfileScreenState.value.copy(
            IsAcceptingRoommates = change
        )
    }

    fun OnProfileDescriptionFieldChange(change: String) {
        _editProfileScreenState.value = editProfileScreenState.value.copy(
            ProfileDescription = change
        )
        validateProfileDescription()
    }

    fun OnUsernameFieldChange(change: String) {
        _editProfileScreenState.value = editProfileScreenState.value.copy(
            Username = change
        )
        validateUsername()
    }

    fun OnFullnameFieldChange(change: String) {
        _editProfileScreenState.value = editProfileScreenState.value.copy(
            Fullname = change
        )
        validateFullname()
    }

    private fun validateProfileDescription() {
        val isTooLong = checkNameLength(
            input = _editProfileScreenState.value.ProfileDescription.length,
            onError = {
                _editProfileScreenState.value.ProfileDescriptionError = ErrorMsgConstants.PROFILE_DESCRIPTION_TOO_LONG
            },
            maximumLength = ValidationConstants.POST_DESCRIPTION_MAX_LENGTH
        )
        val isValid = !isTooLong
        if(isValid) {
            _editProfileScreenState.value.ProfileDescriptionError = ""
        }
        _editProfileScreenState.value.ProfileDescriptionValid = isValid
    }

    private fun validateUsername() {
        val isEmpty = checkIsEmpty(
            input = _editProfileScreenState.value.Username,
            onError = {
                _editProfileScreenState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_IS_EMPTY
            }
        )
        val containsSpecialChar = checkForSpecialChars(
            input = _editProfileScreenState.value.Username,
            onError = {
                _editProfileScreenState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_CONTAINS_SPECIAL_CHAR
            },
            includeUnderline = false
        )
        val hasSpaces = checkForSpaces(
            input = _editProfileScreenState.value.Username,
            onError = {
                _editProfileScreenState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_CONTAINS_SPACE
            }
        )
        val startsWithUnderline = checkStartOfString(
            input = _editProfileScreenState.value.Username,
            illegalChar = '_',
            onError = {
                _editProfileScreenState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_STARTS_WITH_UNDERLINE
            }
        )
        val isTooLong = checkNameLength(
            input = _editProfileScreenState.value.Username.length,
            maximumLength = ValidationConstants.USERNAME_MAX_LENGTH,
            onError = {
                _editProfileScreenState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_TOO_LONG
            }
        )
        val isValid = !isEmpty && !containsSpecialChar && !hasSpaces && !startsWithUnderline && !isTooLong
        if(isValid) {
            _editProfileScreenState.value.UsernameErrorMsg = ""
        }
        _editProfileScreenState.value.UsernameValid = isValid
    }

    private fun validateFullname() {
        val isEmpty = checkIsEmpty(
            input = _editProfileScreenState.value.Fullname,
            onError = {
                _editProfileScreenState.value.FullnameErrorMsg = ErrorMsgConstants.FULLNAME_IS_EMPTY
            }
        )

        val containsSpecialChar = checkForSpecialChars(
            input = _editProfileScreenState.value.Fullname,
            onError = {
                _editProfileScreenState.value.FullnameErrorMsg = ErrorMsgConstants.FULLNAME_CONTAINS_SPECIAL_CHAR
            },
            includeUnderline = true
        )
        val isTooLong = checkNameLength(
            input = _editProfileScreenState.value.Fullname.length,
            maximumLength = ValidationConstants.FULLNAME_MAX_LENGTH,
            onError = {
                _editProfileScreenState.value.FullnameErrorMsg= ErrorMsgConstants.FULLNAME_TOO_LONG
            }
        )
        val isValid = !isEmpty && !containsSpecialChar && !isTooLong
        if(isValid) {
            _editProfileScreenState.value.FullnameErrorMsg = ""
        }
        _editProfileScreenState.value.FullnameValid = isValid
    }

    private suspend fun checkForExistingUsername(): Boolean {
        val db = FirebaseFirestore.getInstance()
        try {
            val exists = db.collection("users").document(_editProfileScreenState.value.Username).get().await().exists()
            if (exists) {
                _editProfileScreenState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_IS_TAKEN
                return true
            }
        } catch (error: Exception) {
            _editProfileScreenState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_CANNOT_BE_VERIFIED
            return true
        }
        return false
    }

    private fun checkForSpaces(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val containsSpace = input.contains(" ")
        if (containsSpace) {
            onError()
        }
        return containsSpace
    }

    private fun checkForSpecialChars(
        input: String,
        onError: () -> Unit,
        includeUnderline: Boolean
    ): Boolean {
        val containsSpecialChar: Boolean = when (includeUnderline) {
            false -> input.contains(regex = Regex("[<>\\/\\\\%&*#\$@^?!()}{:;\"'.,|+=-]"))
            true -> input.contains(regex = Regex("[<>\\/\\\\%&*#\$@^?!()}{:;\"'.,|+=\\-_]"))
        }
        if (containsSpecialChar) {
            onError()
        }
        return containsSpecialChar
    }

    private fun checkStartOfString(
        input: String,
        illegalChar: Char,
        onError: () -> Unit
    ): Boolean {
        val startsWithIllegalChar = input.startsWith(illegalChar)
        if (startsWithIllegalChar) {
            onError()
        }
        return startsWithIllegalChar
    }

    private fun checkIsEmpty(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val isEmpty = input.isEmpty()
        if (isEmpty) {
            onError()
        }
        return isEmpty
    }

    private fun checkNameLength(
        input: Int,
        maximumLength: Int,
        onError: () -> Unit
    ): Boolean {
        val isBeyondMaximumLength = input > maximumLength
        if (isBeyondMaximumLength) {
            onError()
        }
        return isBeyondMaximumLength
    }
}