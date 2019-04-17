package ilyzhin.yetanothermessenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import ilyzhin.yetanothermessenger.mock.MockMessagesProvider
import kotlinx.android.synthetic.main.activity_messages.*

class MessagesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var messageAdapter: MessageAdapter
    private val messagesProvider = MockMessagesProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        rvMessages.layoutManager = layoutManager
        messageAdapter = MessageAdapter(messagesProvider, this)
        rvMessages.adapter = messageAdapter

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
        messagesProvider.addMessage(Message(User.SELF!!, text))
        messageAdapter.notifyItemInserted(messagesProvider.getMessagesCount() - 1);
    }
}
