package ilyzhin.yetanothermessenger.mock

import ilyzhin.yetanothermessenger.IMessagesProvider
import ilyzhin.yetanothermessenger.Message

class MockMessagesProvider : IMessagesProvider {
    val messages = ArrayList<Message>()

    init {
        messages.add(Message("hello"))
        messages.add(Message("how are you"))
        messages.add(Message("fine"))
        messages.add(Message("thank you"))
        messages.add(Message("and you?"))
        messages.add(Message("good"))
        messages.add(Message("bye"))
        messages.add(Message("good bye"))
        messages.add(Message("see you later"))
        messages.add(Message("good luck"))

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