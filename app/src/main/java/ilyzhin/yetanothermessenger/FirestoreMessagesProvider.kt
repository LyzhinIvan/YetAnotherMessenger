package ilyzhin.yetanothermessenger

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreMessagesProvider(private val chatId : String) : IMessagesProvider {
    val messages = ArrayList<Message>()

    override fun sync(callback: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { collection ->
                messages.clear()
                for (message in collection.documents) {
                    val data = message.data!!
                    messages.add(Message(message.id, User.getById((data["from"] as DocumentReference).id), data["text"].toString()))
                }
                callback()
            }
    }

    override fun getMessage(index: Int): Message {
        return messages[index]
    }

    override fun getMessagesCount(): Int {
        return messages.size
    }

    override fun addMessage(message: Message) {
        messages.add(message)
    }

}