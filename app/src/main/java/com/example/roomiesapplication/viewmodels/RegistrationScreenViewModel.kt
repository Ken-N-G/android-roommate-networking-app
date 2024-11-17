package com.example.roomiesapplication.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.example.roomiesapplication.R
import com.example.roomiesapplication.model.ErrorMsgConstants
import com.example.roomiesapplication.model.UserEntryData
import com.example.roomiesapplication.model.UsernameEntryData
import com.example.roomiesapplication.model.ValidationConstants
import com.example.roomiesapplication.viewmodels.states.RegistrationScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RegistrationScreenViewModel: ViewModel() {

    private val _registrationState = mutableStateOf(RegistrationScreenState())
    val registrationState: State<RegistrationScreenState> = _registrationState

    suspend fun Register() {
        if(_registrationState.value.EmailValid && _registrationState.value.PasswordValid && _registrationState.value.FullnameValid && _registrationState.value.UsernameValid && !checkForExistingUsername()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                _registrationState.value.Email,
                _registrationState.value.Password
            ).addOnSuccessListener { result ->
                createUserEntry()
            }.addOnFailureListener { error ->
                _registrationState.value.RegistrationSuccessful = false
                _registrationState.value.RegistrationErrorMsg = error.toString()
            }
        }
    }

    private fun createUserEntry() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val database = FirebaseFirestore.getInstance()
        val newUserEntry = UserEntryData(
            username = _registrationState.value.Username,
            email = _registrationState.value.Email,
            fullname = _registrationState.value.Fullname,
            uid = uid.toString()
        )
        val newUsernameEntry = UsernameEntryData(
            uid = uid.toString()
        )
        database.collection("users").document(uid.toString()).set(newUserEntry).addOnSuccessListener {
            database.collection("usernames").document(_registrationState.value.Username).set(newUsernameEntry).addOnSuccessListener {
                _registrationState.value.RegistrationErrorMsg = ErrorMsgConstants.ON_REGISTRATION_SUCCESS
                _registrationState.value.RegistrationSuccessful = true
            }

        }
    }

    fun OnEmailFieldChange(change: String) {
        _registrationState.value = registrationState.value.copy(
            Email = change
        )
        validateEmail()
    }

    fun OnPasswordFieldChange(change: String) {
        _registrationState.value = registrationState.value.copy(
            Password = change
        )
        validatePassword()
    }

    fun OnUsernameFieldChange(change: String) {
        _registrationState.value = registrationState.value.copy(
            Username = change
        )
        validateUsername()
    }

    fun OnFullnameFieldChange(change: String) {
        _registrationState.value = registrationState.value.copy(
            Fullname = change
        )
        validateFullname()
    }

    private fun validateEmail() {
        val isEmpty = checkIsEmpty(
            input = _registrationState.value.Email,
            onError = {
                _registrationState.value.EmailErrorMsg = ErrorMsgConstants.EMAIL_IS_EMPTY
            }
        )
        val isValid = !isEmpty
        if(isValid) {
            _registrationState.value.EmailErrorMsg = ""
        }
        _registrationState.value.EmailValid = isValid
    }

    private fun validatePassword() {
        val doesNotContainSpecialChar = checkSpecialCharInclusion(
            input = _registrationState.value.Password,
            onError = {
                _registrationState.value.PasswordErrorMsg = ErrorMsgConstants.PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHAR
            }
        )
        val isTooShort = checkPasswordLength(
            input = _registrationState.value.Password.length,
            onError = {
                _registrationState.value.PasswordErrorMsg = ErrorMsgConstants.PASSWORD_TOO_SHORT
            },
            minimumLength = ValidationConstants.PASSWORD_MIN_LENGTH
        )
        val isEmpty = checkIsEmpty(
            input = _registrationState.value.Password,
            onError = {
                _registrationState.value.PasswordErrorMsg = ErrorMsgConstants.PASSWORD_IS_EMPTY
            }
        )
        val hasSpaces = checkForSpaces(
            input = _registrationState.value.Username,
            onError = {
                _registrationState.value.PasswordErrorMsg = ErrorMsgConstants.PASSWORD_CONTAINS_SPACE
            }
        )
        val isValid = !isEmpty && !doesNotContainSpecialChar && !hasSpaces && !isTooShort
        if(isValid) {
            _registrationState.value.PasswordErrorMsg = ""
        }
        _registrationState.value.PasswordValid = isValid
    }

    private fun validateUsername() {
        val isEmpty = checkIsEmpty(
            input = _registrationState.value.Username,
            onError = {
                _registrationState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_IS_EMPTY
            }
        )
        val containsSpecialChar = checkForSpecialChars(
            input = _registrationState.value.Username,
            onError = {
                _registrationState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_CONTAINS_SPECIAL_CHAR
            },
            includeUnderline = false
        )
        val hasSpaces = checkForSpaces(
            input = _registrationState.value.Username,
            onError = {
                _registrationState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_CONTAINS_SPACE
            }
        )
        val startsWithUnderline = checkStartOfString(
            input = _registrationState.value.Username,
            illegalChar = '_',
            onError = {
                _registrationState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_STARTS_WITH_UNDERLINE
            }
        )
        val isTooLong = checkNameLength(
            input = _registrationState.value.Username.length,
            maximumLength = ValidationConstants.USERNAME_MAX_LENGTH,
            onError = {
                _registrationState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_TOO_LONG
            }
        )
        val isValid = !isEmpty && !containsSpecialChar && !hasSpaces && !startsWithUnderline && !isTooLong
        if(isValid) {
            _registrationState.value.UsernameErrorMsg = ""
        }
        _registrationState.value.UsernameValid = isValid
    }

    private fun validateFullname() {
        val isEmpty = checkIsEmpty(
            input = _registrationState.value.Fullname,
            onError = {
                _registrationState.value.FullnameErrorMsg = ErrorMsgConstants.FULLNAME_IS_EMPTY
            }
        )

        val containsSpecialChar = checkForSpecialChars(
            input = _registrationState.value.Fullname,
            onError = {
                _registrationState.value.FullnameErrorMsg = ErrorMsgConstants.FULLNAME_CONTAINS_SPECIAL_CHAR
            },
            includeUnderline = true
        )
        val isTooLong = checkNameLength(
            input = _registrationState.value.Fullname.length,
            maximumLength = ValidationConstants.FULLNAME_MAX_LENGTH,
            onError = {
                _registrationState.value.FullnameErrorMsg= ErrorMsgConstants.FULLNAME_TOO_LONG
            }
        )
        val isValid = !isEmpty && !containsSpecialChar && !isTooLong
        if(isValid) {
            _registrationState.value.FullnameErrorMsg = ""
        }
        _registrationState.value.FullnameValid = isValid
    }

    private suspend fun checkForExistingUsername(): Boolean {
        val db = FirebaseFirestore.getInstance()
        try {
            val exists = db.collection("usernames").document(_registrationState.value.Username.trim()).get().await().exists()
            if (exists) {
                _registrationState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_IS_TAKEN
                return true
            }
        } catch (error: Exception) {
            _registrationState.value.UsernameErrorMsg = ErrorMsgConstants.USERNAME_CANNOT_BE_VERIFIED
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

    private fun checkSpecialCharInclusion(
        input: String,
        onError: () -> Unit
    ): Boolean {
        val doesNotContainSpecialChar = !input.contains(regex = Regex("[<>\\/\\\\%&*#\$@^?!()}{:;\"'.,|+=\\-_]"))
        if (doesNotContainSpecialChar) {
            onError()
        }
        return doesNotContainSpecialChar
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

    private fun checkPasswordLength(
        input: Int,
        minimumLength: Int,
        onError: () -> Unit
    ): Boolean {
        val isBelowMinimumLength = input < minimumLength
        if (isBelowMinimumLength) {
            onError()
        }
        return isBelowMinimumLength
    }
}