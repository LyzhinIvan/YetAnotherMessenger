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
        val friend = User(UUID.randomUUID().toString(), "friend")
        messages.add(Message(UUID.randomUUID().toString(), user, "hello"))
        messages.add(Message(UUID.randomUUID().toString(), user,"how are you"))
        messages.add(Message(UUID.randomUUID().toString(), friend,"fine"))
        messages.add(Message(UUID.randomUUID().toString(), friend,"thank you"))
        messages.add(Message(UUID.randomUUID().toString(), friend,"and you?"))
        messages.add(Message(UUID.randomUUID().toString(), user,"good"))
        messages.add(Message(UUID.randomUUID().toString(), user,"bye"))
        messages.add(Message(UUID.randomUUID().toString(), friend,"good bye"))
        messages.add(Message(UUID.randomUUID().toString(), user,"see you later"))
        messages.add(Message(UUID.randomUUID().toString(), friend, "good luck"))

    }

    override fun sync(callback: () -> Unit) {

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