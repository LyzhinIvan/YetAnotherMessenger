package ilyzhin.yetanothermessenger

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import ilyzhin.yetanothermessenger.models.Chat
import kotlinx.android.synthetic.main.chat_list_item.view.*


class ChatsAdapter(val context : Context) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var chats : List<Chat> = arrayListOf()

    fun setChats(chats : List<Chat>) {
        this.chats = chats
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = inflater.inflate(R.layout.chat_list_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount() = chats.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chats[position]
        holder.tvChatName.text = chat.title
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MessagesActivity::class.java)
            intent.putExtra(Constants.CHAT_ID, chat.id)
            startActivity(context, intent, Bundle.EMPTY)
        }
    }

    inner class ChatViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val tvChatName: TextView = view.tvChatName
    }
}