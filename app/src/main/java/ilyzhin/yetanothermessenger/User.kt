package ilyzhin.yetanothermessenger

import java.util.*

class User(val id : UUID, val name : String) {
    companion object {
        var SELF : User? = null

        fun authenticate() {
            SELF = User(UUID.randomUUID(), "Test User")
        }
    }


}