package ilyzhin.yetanothermessenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ilyzhin.yetanothermessenger.models.Message
import kotlinx.android.synthetic.main.message_list_item.view.*

class MessagesAdapter(val context : Context) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var messages : List<Message> = emptyList()
    private val currentUserId : String = FirebaseAuth.getInstance().currentUser!!.uid

    fun setMessages(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = inflater.inflate(R.layout.message_list_item, parent, false);
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = messages[position]
        holder.tvMessageText.text = msg.text
        val anchorSide = if (msg.from == currentUserId) ConstraintSet.END else ConstraintSet.START
        val set = ConstraintSet()
        set.clone(holder.itemView.rootLayout)
        set.clear(holder.tvMessageText.id, ConstraintSet.START)
        set.clear(holder.tvMessageText.id, ConstraintSet.END)
        set.connect(holder.tvMessageText.id, anchorSide, ConstraintSet.PARENT_ID, anchorSide, 8);
        set.applyTo(holder.itemView.rootLayout)

    }

    inner class MessageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvMessageText: TextView = view.tvMessageText
    }
}