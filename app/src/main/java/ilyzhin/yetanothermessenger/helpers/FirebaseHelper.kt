package ilyzhin.yetanothermessenger.helpers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import ilyzhin.yetanothermessenger.YamMessagingService
import ilyzhin.yetanothermessenger.models.User
import javax.security.auth.callback.Callback

object FirebaseHelper {
    private lateinit var currentUser : FirebaseUser
    private lateinit var userRef : DocumentReference

    fun onUserSignIn(callback: () -> Unit) {
        currentUser = FirebaseAuth.getInstance().currentUser!!
        userRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.uid)
        userRef.get()
            .addOnSuccessListener {
                if (!it.exists()) {
                    val user = User(currentUser.displayName!!)
                    userRef.set(user)
                }
                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener{task ->
                        YamMessagingService.addToken(task.result?.token)
                    }
                callback()
            }

    }
}