package ilyzhin.yetanothermessenger

import Constants
import Constants.LOG_TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import ilyzhin.yetanothermessenger.helpers.FirebaseHelper
import ilyzhin.yetanothermessenger.models.Message
import kotlinx.android.synthetic.main.activity_messages.*

class MessagesActivity : AppCompatActivity() {

    private lateinit var adapter : MessagesAdapter
    private lateinit var chatId : String
    private lateinit var messagesRef : CollectionReference
    private val currentUserId : String = FirebaseAuth.getInstance().currentUser!!.uid


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        chatId = intent.getStringExtra(Constants.CHAT_ID)
        supportActionBar?.title = intent.getStringExtra(Constants.CHAT_TITLE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        FirebaseHelper.isCurrentUserInChat(chatId) {
            if (it) {
                btnJoinChat.visibility = View.INVISIBLE
                btnSendMessage.visibility = View.VISIBLE
                etMessageInput.visibility = View.VISIBLE
            } else {
                btnJoinChat.visibility = View.VISIBLE
                btnSendMessage.visibility = View.INVISIBLE
                etMessageInput.visibility = View.INVISIBLE
            }
        }

        messagesRef = FirebaseFirestore.getInstance().collection("chats").document(chatId).collection("messages")
        initRecycler()
        loadMessages()

        btnSendMessage.setOnClickListener {
            val msgText = etMessageInput.text
            if (!msgText.isBlank()) {
                messagesRef.add(
                    Message(currentUserId, msgText.toString(), Timestamp.now())
                )
                msgText.clear()
            }
        }

        btnJoinChat.setOnClickListener {
            btnJoinChat.visibility = View.INVISIBLE
            btnSendMessage.visibility = View.VISIBLE
            etMessageInput.visibility = View.VISIBLE
            FirebaseHelper.joinChat(chatId, currentUserId)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initRecycler() {
        adapter = MessagesAdapter(this)
        rvMessages.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        rvMessages.layoutManager = layoutManager
    }

    private fun loadMessages() {
        messagesRef.orderBy("timestamp")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.d(LOG_TAG, "Exception: $exception")
                    return@addSnapshotListener
                }

                val messages = mutableListOf<Message>()
                snapshot!!.documents.forEach{doc ->
                    val message : Message = doc.toObject(Message::class.java)!!.withId(doc.id)
                    messages.add(message)
                }
                adapter.setMessages(messages)
                rvMessages.scrollToPosition(messages.size - 1)
            }
    }

}
