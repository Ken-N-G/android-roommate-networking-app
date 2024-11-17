package com.example.roomiesapplication.viewmodels

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomiesapplication.model.ErrorMsgConstants
import com.example.roomiesapplication.viewmodels.states.ProfileScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileScreenViewModel: ViewModel() {

    private val _profileScreenState = MutableStateFlow((ProfileScreenState()))
    val profileScreenState: StateFlow<ProfileScreenState> = _profileScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            FetchUserData()
        }
    }

    fun onURIResult(uri: Uri?, context: android.content.Context) {
        viewModelScope.launch {
            try {
                val reference = FirebaseStorage.getInstance().reference
                val childReference = reference.child(_profileScreenState.value.Uid)
                val db = FirebaseFirestore.getInstance()
                if(uri != null) {
                    val task = childReference.putFile(uri)
                    task.continueWithTask() { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                _profileScreenState.value.ProfileErrorMsg = task.exception?.toString() ?: ""
                                throw it
                            }
                        }
                        childReference.downloadUrl.addOnSuccessListener { downloadUri ->
                            db.collection("users").document(_profileScreenState.value.Uid).update("profileURL", downloadUri.toString())
                            _profileScreenState.value.ProfileErrorMsg = ErrorMsgConstants.ON_UPLOAD_SUCCESS
                            Toast.makeText(
                                context,
                                _profileScreenState.value.ProfileErrorMsg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }.addOnFailureListener() {
                            _profileScreenState.value.ProfileErrorMsg = it.message ?: ""
                            Toast.makeText(
                                context,
                                _profileScreenState.value.ProfileErrorMsg,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (error: Exception) {
                Toast.makeText(
                    context,
                    _profileScreenState.value.ProfileErrorMsg,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun Logout() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    suspend fun FetchUserData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val db = FirebaseFirestore.getInstance()
        try {
            val docSnapshot = db.collection("users").document(uid).get().await()
            _profileScreenState.value = _profileScreenState.value.copy(
                Username = docSnapshot.getString("username").orEmpty(),
                Fullname = docSnapshot.getString("fullname").orEmpty(),
                ProfileDescription = docSnapshot.getString("profileDescription").orEmpty(),
                ProfileURL = docSnapshot.getString("profileURL").orEmpty(),
                Uid = uid,
                AcceptingStatus = docSnapshot.getBoolean("acceptingStatus") ?: false
            )
        } catch (error: Exception) {
            _profileScreenState.value.ProfileErrorMsg = error.toString()
        }
    }
}