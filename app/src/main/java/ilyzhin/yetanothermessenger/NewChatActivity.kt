package ilyzhin.yetanothermessenger

import Constants
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ilyzhin.yetanothermessenger.helpers.FirebaseHelper
import kotlinx.android.synthetic.main.activity_new_chat.*

class NewChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat)

        btnCreate.setOnClickListener {
            FirebaseHelper.createChat(etChatTitle.text.toString())
                .addOnSuccessListener {chatId ->
                    val intent = Intent(this, MessagesActivity::class.java)
                    intent.putExtra(Constants.CHAT_ID, chatId)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    ContextCompat.startActivity(this, intent, Bundle.EMPTY)
                    finish()
                }
        }
    }
}
