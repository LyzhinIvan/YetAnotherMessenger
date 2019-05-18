package ilyzhin.yetanothermessenger.models

import com.google.firebase.Timestamp

data class Message(val id : String, val userId : String, val text : String, val timestamp: Timestamp)