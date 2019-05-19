package ilyzhin.yetanothermessenger.models

import com.google.firebase.Timestamp

data class Message(val from : String = "",
                   val text : String = "",
                   val timestamp: Timestamp = Timestamp.now()) : Model()