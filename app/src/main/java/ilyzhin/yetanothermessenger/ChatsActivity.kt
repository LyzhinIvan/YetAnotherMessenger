package ilyzhin.yetanothermessenger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import ilyzhin.yetanothermessenger.mock.MockChatsProvider
import kotlinx.android.synthetic.main.activity_chats.*

class ChatsActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser == null) {
            rvChats.visibility = View.GONE
            btnSignIn.visibility = View.VISIBLE
            btnSignIn.setOnClickListener { signIn() }
        } else {
            btnSignIn.visibility = View.GONE
            loadChats()
            rvChats.visibility = View.VISIBLE
        }
    }

    private fun signIn() {
        val signProviders = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(signProviders)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun loadChats() {
        mAuth.currentUser?.let {
            val chatsProvider = MockChatsProvider()
            rvChats.layoutManager = LinearLayoutManager(this)
            rvChats.adapter = ChatAdapter(chatsProvider, this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                btnSignIn.visibility = View.GONE
                loadChats()
                rvChats.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, response!!.error.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 101
    }
}
