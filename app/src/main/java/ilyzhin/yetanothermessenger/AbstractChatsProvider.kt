package ilyzhin.yetanothermessenger

abstract class AbstractChatsProvider : IChatsProvider {
    override fun sync(callback: () -> Unit) = callback()
}