package ilyzhin.yetanothermessenger

interface IMessagesProvider {
    fun sync(callback : () -> Unit)
    fun getMessage(index : Int) : Message
    fun getMessagesCount() : Int
    fun addMessage(message : Message)
}