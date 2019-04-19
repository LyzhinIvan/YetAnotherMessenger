package ilyzhin.yetanothermessenger

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
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
                    (document["chats"] as List<DocumentReference>).let { chatsRefs ->
                        for (chatRef in chatsRefs) {
                            chatRef.get()
                                .addOnSuccessListener { result ->
                                    chats.add(Chat(result.data!!["title"].toString()))
                                    callback()
                                }
                                .addOnFailureListener { exception ->
                                    Log.d("MYAPP", "failed to load chat", exception)
                                }
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
        Log.d("MYAPP", "chats size " + chats.size)
        return chats.size;
    }
}