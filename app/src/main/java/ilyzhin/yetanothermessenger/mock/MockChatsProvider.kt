package ilyzhin.yetanothermessenger.mock

import ilyzhin.yetanothermessenger.AbstractChatsProvider
import ilyzhin.yetanothermessenger.Chat

class MockChatsProvider : AbstractChatsProvider() {
    private val chats = ArrayList<Chat>()

    init {
        chats.add(Chat("University chat 1"))
        chats.add(Chat("University chat 2"))
        chats.add(Chat("University chat 3"))
        chats.add(Chat("University chat 4"))
        chats.add(Chat("University chat 5"))
        chats.add(Chat("University chat 6"))
        chats.add(Chat("School chat 1"))
        chats.add(Chat("School chat 2"))
        chats.add(Chat("School chat 3"))
        chats.add(Chat("School chat 4"))
        chats.add(Chat("School chat 5"))
        chats.add(Chat("School chat 6"))
        chats.add(Chat("Friend 1"))
        chats.add(Chat("Friend 2"))
        chats.add(Chat("Friend 3"))
        chats.add(Chat("Friend 4"))
        chats.add(Chat("Friend 5"))
        chats.add(Chat("Friend 6"))
        chats.add(Chat("Friend 7"))
    }



    override fun getChatsCount(): Int {
        return chats.size
    }

    override fun getChat(index: Int): Chat {
        return chats[index]
    }
}