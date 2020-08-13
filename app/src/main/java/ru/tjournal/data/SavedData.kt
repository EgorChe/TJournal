package ru.tjournal.data

import android.content.SharedPreferences
import ru.tjournal.util.string

class SavedData(
    sharedPreferences: SharedPreferences
) {

    var userName: String by sharedPreferences.string(USER_NAME)
    var userAvatar: String by sharedPreferences.string(USER_AVATAR)

    companion object {
        private const val USER_NAME = "USER_NAME"
        private const val USER_AVATAR = "USER_AVATAR"
    }
}