package ilyzhin.yetanothermessenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_messages.*
import java.util.*

class MessagesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagesProvider : IMessagesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        rvMessages.layoutManager = layoutManager
        messagesProvider = FirestoreMessagesProvider(intent.extras.getString("chatId"))
        messageAdapter = MessageAdapter(messagesProvider, this)
        rvMessages.adapter = messageAdapter
        messagesProvider.sync { messageAdapter.notifyDataSetChanged() }

        ibSendMessage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == null) return
        when (v.id) {
            R.id.ibSendMessage -> {
                if (etMessageInput.text.isEmpty()) return
                sendMessage(etMessageInput.text.toString())
                etMessageInput.text.clear()
            }
        }
    }

    private fun sendMessage(text: String) {
        messagesProvider.addMessage(Message(UUID.randomUUID().toString(), User.SELF!!, text))
        messageAdapter.notifyItemInserted(messagesProvider.getMessagesCount() - 1);
        rvMessages.scrollToPosition(messagesProvider.getMessagesCount() - 1)
    }
}
