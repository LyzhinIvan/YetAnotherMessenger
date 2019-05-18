package ilyzhin.yetanothermessenger

import Constants.LOG_TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class YamMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(LOG_TAG, "REMOTE MESSAGE " + remoteMessage?.notification?.body)
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)

        addToken(token)
    }

    companion object {
        fun addToken(token : String?) {
            val currentUser = FirebaseAuth.getInstance().currentUser
            Log.d(LOG_TAG, "TOKEN $token")
            if (currentUser != null && token != null) {
                val userRef = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(currentUser.uid)
                userRef
                    .get()
                    .addOnSuccessListener {
                        val tokens = (it["tokens"] as MutableList<String>?) ?: mutableListOf()
                        if (token !in tokens) {
                            tokens.add(token)
                            userRef.update("tokens", tokens)
                        }
                    }
            }
        }
    }
}