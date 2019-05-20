package ilyzhin.yetanothermessenger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import ilyzhin.yetanothermessenger.helpers.FirebaseHelper
import ilyzhin.yetanothermessenger.models.Message
import ilyzhin.yetanothermessenger.models.User
import kotlinx.android.synthetic.main.message_list_item.view.*

class MessagesAdapter(val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)
    private var messages : List<Message> = emptyList()
    private val currentUserId : String = FirebaseAuth.getInstance().currentUser!!.uid

    companion object {
        const val SELF_MESSAGE_VIEW_TYPE = 1
        const val OTHER_MESSAGE_VIEW_TYPE = 2
    }

    fun setMessages(messages: List<Message>) {
        this.messages = messages
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].from == currentUserId) SELF_MESSAGE_VIEW_TYPE;
        else OTHER_MESSAGE_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == SELF_MESSAGE_VIEW_TYPE)
            SelfMessageViewHolder(inflater.inflate(R.layout.message_list_item_self, parent, false))
        else
            OtherMessageViewHolder(inflater.inflate(R.layout.message_list_item, parent, false))
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = messages[position]
        if (holder is SelfMessageViewHolder) {
            holder.tvMessageText.text = msg.text
        } else if (holder is OtherMessageViewHolder) {
            holder.tvMessageText.text = msg.text
            // load image
            FirebaseHelper.getUser(msg.from)
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)!!
                    if (!user.photoId.isNullOrBlank()) {
                        val photoRef = FirebaseStorage.getInstance().getReference("avatars/${user.photoId}")
                        Glide.with(context).load(photoRef).into(holder.ivAvatar)
                    }
                }
        }
    }

    inner class SelfMessageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvMessageText: TextView = view.tvMessageText
    }

    inner class OtherMessageViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvMessageText: TextView = view.tvMessageText
        val ivAvatar : ImageView = view.ivPhoto
    }
}