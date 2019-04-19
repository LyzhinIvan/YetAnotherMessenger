package ilyzhin.yetanothermessenger.mock

import ilyzhin.yetanothermessenger.AbstractChatsProvider
import ilyzhin.yetanothermessenger.Chat
import java.util.*
import kotlin.collections.ArrayList

class MockChatsProvider : AbstractChatsProvider() {
    private val chats = ArrayList<Chat>()

    init {
        chats.add(Chat(UUID.randomUUID().toString(),"University chat 1"))
        chats.add(Chat(UUID.randomUUID().toString(),"University chat 2"))
        chats.add(Chat(UUID.randomUUID().toString(),"University chat 3"))
        chats.add(Chat(UUID.randomUUID().toString(),"University chat 4"))
        chats.add(Chat(UUID.randomUUID().toString(),"University chat 5"))
        chats.add(Chat(UUID.randomUUID().toString(),"University chat 6"))
        chats.add(Chat(UUID.randomUUID().toString(),"School chat 1"))
        chats.add(Chat(UUID.randomUUID().toString(),"School chat 2"))
        chats.add(Chat(UUID.randomUUID().toString(),"School chat 3"))
        chats.add(Chat(UUID.randomUUID().toString(),"School chat 4"))
        chats.add(Chat(UUID.randomUUID().toString(),"School chat 5"))
        chats.add(Chat(UUID.randomUUID().toString(),"School chat 6"))
        chats.add(Chat(UUID.randomUUID().toString(),"Friend 1"))
        chats.add(Chat(UUID.randomUUID().toString(),"Friend 2"))
        chats.add(Chat(UUID.randomUUID().toString(),"Friend 3"))
        chats.add(Chat(UUID.randomUUID().toString(),"Friend 4"))
        chats.add(Chat(UUID.randomUUID().toString(),"Friend 5"))
        chats.add(Chat(UUID.randomUUID().toString(),"Friend 6"))
        chats.add(Chat(UUID.randomUUID().toString(),"Friend 7"))
    }



    override fun getChatsCount(): Int {
        return chats.size
    }

    override fun getChat(index: Int): Chat {
        return chats[index]
    }
}