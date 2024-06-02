package com.example.pi_sniffsnap_garciafernandezmarta.model

import android.app.Activity
import android.content.Context

class UserGoogle(
    val uid: String,
    val email: String,
    val idToken: String
) {
    companion object {
        private const val GOOGLE_AUTH_PREFS = "google_auth_prefs"
        private const val UID_KEY = "uid"
        private const val EMAIL_KEY = "email"
        private const val ID_TOKEN_KEY = "id_token"

        fun setLoggedInUser(activity: Activity, user: UserGoogle) {
            activity.getSharedPreferences(GOOGLE_AUTH_PREFS, Context.MODE_PRIVATE).also {
                it.edit()
                    .putString(UID_KEY, user.uid)
                    .putString(EMAIL_KEY, user.email)
                    .putString(ID_TOKEN_KEY, user.idToken)
                    .apply()
            }
        }

        fun getLoggedInUser(activity: Activity): UserGoogle? {
            val prefs = activity.getSharedPreferences(GOOGLE_AUTH_PREFS, Context.MODE_PRIVATE) ?: return null

            val uid = prefs.getString(UID_KEY, "") ?: return null
            val email = prefs.getString(EMAIL_KEY, "") ?: return null
            val idToken = prefs.getString(ID_TOKEN_KEY, "") ?: return null

            return UserGoogle(uid, email, idToken)
        }

        fun logout(activity: Activity) {
            activity.getSharedPreferences(GOOGLE_AUTH_PREFS, Context.MODE_PRIVATE).also {
                it.edit().clear().apply()
            }
        }
    }
}
