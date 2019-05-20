package ilyzhin.yetanothermessenger.helpers

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import ilyzhin.yetanothermessenger.YamMessagingService
import ilyzhin.yetanothermessenger.models.Chat
import ilyzhin.yetanothermessenger.models.User
import org.jetbrains.anko.doAsync
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

    fun createChat(title : String) : Task<String> {
        val chat = Chat(title, mutableListOf(currentUser.uid))
        val chatRef = FirebaseFirestore.getInstance().collection("chats").document()
        AlgoliaHelper.addChatToIndex(chat.withId(chatRef.id))
        return chatRef.set(chat)
            .continueWith {
                userRef.get().addOnSuccessListener {
                    val user = it.toObject(User::class.java)!!
                    user.chats.add(chatRef.id)
                    userRef.set(user)
                }
            }.continueWith {
                chatRef.id
            }
    }

    fun joinChat(chatId: String, userId: String) {
        val chatRef = FirebaseFirestore.getInstance().collection("chats").document(chatId)
        chatRef.get()
            .addOnSuccessListener {
                val chat = it.toObject(Chat::class.java)!!
                chat.users.add(userId)
                chatRef.set(chat)
            }
        userRef.get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)!!
                user.chats.add(chatId)
                userRef.set(user)
            }
    }

    fun isCurrentUserInChat(chatId: String, callback: (Boolean) -> Any) {
        userRef.get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)!!
                callback(chatId in user.chats)
            }
    }
}