package ilyzhin.yetanothermessenger

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class User(val id : String, val name : String) {
    companion object {
        var SELF : User? = null

        fun getById(id : String) : User {
            return if (SELF?.id == id) SELF!! else User(id, "name")
        }
    }

    lateinit var ref : DocumentReference

    fun sync() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(id)
            .get()
            .addOnSuccessListener {
                ref = it.reference
            }
    }


}