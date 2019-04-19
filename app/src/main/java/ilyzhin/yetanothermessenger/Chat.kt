package ilyzhin.yetanothermessenger

class Chat(val id : String, val chatName: String) {

    fun getUnreadMessagesCount() : Int {
        return 100;
    }
}