package com.example.roomiesapplication.viewmodels.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.roomiesapplication.viewmodels.PersonalChatScreenViewModel

@Suppress("UNCHECKED_CAST")
class PersonalChatViewModelFactory(private val uid: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PersonalChatScreenViewModel(uid) as T
    }
}