package ilyzhin.yetanothermessenger

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreChatsProvider : AbstractChatsProvider() {
    private var chats : ArrayList<Chat> = ArrayList()

    override fun sync(callback: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                chats.clear()
                document.let {
                    (document["chats"] as List<*>).let { chatsRefs ->
                        for (chatRef in chatsRefs) {
                            chats.add(Chat(chatRef.toString()))
                        }

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("MYAPP", "Error while getting users.", exception)
            }
            .addOnCompleteListener {
                callback()
            }
    }

    override fun getChat(index: Int): Chat {
        return chats[index]
    }

    override fun getChatsCount(): Int {
        return chats.size;
    }
}