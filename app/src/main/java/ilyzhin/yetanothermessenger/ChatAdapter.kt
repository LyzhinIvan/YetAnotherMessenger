package ilyzhin.yetanothermessenger

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.chat_list_item.view.*

class ChatAdapter(val chatsProvider : IChatsProvider, val context : Context)
        : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun getItemCount(): Int {
        return chatsProvider.getChatsCount()
    }

    override fun onBindViewHolder(viewHolder: ChatViewHolder, position: Int) {
        val chat = chatsProvider.getChat(position)
        viewHolder.tvChatName.text = chat.chatName
        viewHolder.tvUnreadMessagesCount.text = chat.getUnreadMessagesCount().toString()
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, MessagesActivity::class.java)
            intent.putExtra("chatId", chat.id)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_list_item, parent, false)
        return ChatViewHolder(view)
    }

    class ChatViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        val ivAvatar = view.ivAvatar
        val tvChatName = view.tvChatName
        val tvUnreadMessagesCount = view.tvUnreadMessagesCount
    }
}