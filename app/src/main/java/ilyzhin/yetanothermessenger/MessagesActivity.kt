package ilyzhin.yetanothermessenger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_messages.*

class MessagesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messagesProvider : IMessagesProvider
    private lateinit var chatId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        chatId = intent.extras.getString("chatId")

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        rvMessages.layoutManager = layoutManager
        messagesProvider = FirestoreMessagesProvider(chatId)
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
        val data = HashMap<String, Any>()
        data["timestamp"] = Timestamp.now()
        data["from"] = User.SELF!!.ref
        data["text"] = text
        val db = FirebaseFirestore.getInstance()
        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(data)
            .addOnCompleteListener { messagesProvider.sync { messageAdapter.notifyDataSetChanged() } }
    }
}
