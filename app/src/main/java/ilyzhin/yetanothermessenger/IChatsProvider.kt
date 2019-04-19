package ilyzhin.yetanothermessenger

interface IChatsProvider {
    fun getChat(index : Int) : Chat
    fun getChatsCount() : Int
    fun sync(callback : () -> Unit)
}