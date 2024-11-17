package com.example.roomiesapplication.model

object ErrorMsgConstants {
    const val USERNAME_IS_EMPTY = "Your username cannot be empty!"
    const val USERNAME_IS_TAKEN = "Your username has already been taken!"
    const val USERNAME_CANNOT_BE_VERIFIED = "Your username cannot be verified! Try again later."
    const val USERNAME_CONTAINS_SPACE = "Your username cannot contain spaces!"
    const val USERNAME_CONTAINS_SPECIAL_CHAR = "Your username cannot contain special characters!"
    const val USERNAME_STARTS_WITH_UNDERLINE = "Your username cannot start with an underline!"
    const val USERNAME_TOO_LONG = "Your username is too long!"
    const val PASSWORD_IS_EMPTY = "Your password cannot be empty!"
    const val PASSWORD_TOO_SHORT = "Your password is too short!"
    const val PASSWORD_DOES_NOT_CONTAIN_SPECIAL_CHAR = "Add a unique character to your password!"
    const val PASSWORD_CONTAINS_SPACE = "Your password cannot contain spaces!"
    const val EMAIL_IS_EMPTY = "Your email cannot be empty!"
    const val FULLNAME_IS_EMPTY = "Your full name cannot be empty!"
    const val FULLNAME_CONTAINS_SPECIAL_CHAR = "Your full name cannot contain special characters!"
    const val FULLNAME_TOO_LONG = "Your full name is too long!"
    const val ON_SIGNIN_SUCCESS = "Sign in was successful!"
    const val ON_REGISTRATION_SUCCESS = "Registration was successful!"
    const val POST_IS_EMPTY = "Your post cannot be empty!"
    const val ON_POST_SUCCESS = "Adding your post was successful!"
    const val ON_UPLOAD_SUCCESS = "Uploading your profile picture was a success!"
    const val PROFILE_DESCRIPTION_TOO_LONG = "Your description is too long!"
    const val ON_UPDATE_SUCCESS = "Your profile has been updated!"
    const val ON_UPDATE_FAILURE = "There are a few errors in your input!"
}

object ValidationConstants {
    const val USERNAME_MAX_LENGTH: Int = 20
    const val PASSWORD_MIN_LENGTH: Int = 8
    const val FULLNAME_MAX_LENGTH: Int = 40
    const val POST_DESCRIPTION_MAX_LENGTH: Int = 80
}
