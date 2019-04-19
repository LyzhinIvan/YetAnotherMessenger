package ilyzhin.yetanothermessenger

import java.util.*

class User(val id : String, val name : String) {
    companion object {
        var SELF : User? = null

        fun authenticate() {
            SELF = User(UUID.randomUUID().toString(), "Test User")
        }

        fun getById(id : String) : User {
            return if (SELF?.id == id) SELF!! else User(id, "name")
        }
    }


}