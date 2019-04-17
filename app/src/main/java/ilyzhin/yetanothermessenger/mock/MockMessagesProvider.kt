package ilyzhin.yetanothermessenger.mock

import ilyzhin.yetanothermessenger.IMessagesProvider
import ilyzhin.yetanothermessenger.Message
import ilyzhin.yetanothermessenger.User
import java.util.*
import kotlin.collections.ArrayList

class MockMessagesProvider : IMessagesProvider {
    val messages = ArrayList<Message>()

    init {
        if (User.SELF == null) {
            User.authenticate()
        }
        val user = User.SELF!!
        val friend = User(UUID.randomUUID(), "friend")
        messages.add(Message(user, "hello"))
        messages.add(Message(user,"how are you"))
        messages.add(Message(friend,"fine"))
        messages.add(Message(friend,"thank you"))
        messages.add(Message(friend,"and you?"))
        messages.add(Message(user,"good"))
        messages.add(Message(user,"bye"))
        messages.add(Message(friend,"good bye"))
        messages.add(Message(user,"see you later"))
        messages.add(Message(friend, "good luck"))

    }

    override fun getMessagesCount(): Int {
        return messages.size
    }

    override fun getMessage(index: Int): Message {
        return messages[index]
    }

    override fun addMessage(message: Message) {
        messages.add(message)
    }
}