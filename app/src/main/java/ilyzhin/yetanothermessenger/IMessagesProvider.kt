package ilyzhin.yetanothermessenger

interface IMessagesProvider {
    fun getMessage(index : Int) : Message
    fun getMessagesCount() : Int
    fun addMessage(message : Message)
}