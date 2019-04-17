package ilyzhin.yetanothermessenger

import android.content.Context
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.message_list_item.view.*

class MessageAdapter(val messagesProvider: IMessagesProvider, val context : Context) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.message_list_item, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messagesProvider.getMessagesCount()
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {
        val message = messagesProvider.getMessage(position)
        viewHolder.tvMessageText.text = message.text
        val anchorSide = if (message.from == User.SELF) ConstraintSet.END else ConstraintSet.START
        val set = ConstraintSet()
        set.clone(viewHolder.itemView.rootLayout)
        set.clear(viewHolder.tvMessageText.id, ConstraintSet.START)
        set.clear(viewHolder.tvMessageText.id, ConstraintSet.END)
        set.connect(viewHolder.tvMessageText.id, anchorSide, ConstraintSet.PARENT_ID, anchorSide, 8);
        set.applyTo(viewHolder.itemView.rootLayout)
    }

    class MessageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvMessageText: TextView = view.tvMessageText
    }

}