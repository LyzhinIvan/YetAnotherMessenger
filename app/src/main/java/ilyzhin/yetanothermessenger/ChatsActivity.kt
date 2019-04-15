package ilyzhin.yetanothermessenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import ilyzhin.yetanothermessenger.mock.MockChatsProvider
import kotlinx.android.synthetic.main.activity_chats.*
import java.util.*

class ChatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        val chatsProvider = MockChatsProvider()
        rvChats.layoutManager = LinearLayoutManager(this)
        rvChats.adapter = ChatAdapter(chatsProvider, this)
    }
}
