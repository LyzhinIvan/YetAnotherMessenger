package ilyzhin.yetanothermessenger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.iid.FirebaseInstanceId
import org.jetbrains.anko.startActivity

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val signInProviders = arrayListOf(AuthUI.IdpConfig.EmailBuilder().setRequireName(true).build())
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(signInProviders)
            .build()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                FirebaseInstanceId.getInstance().instanceId
                    .addOnCompleteListener{task ->
                        YamMessagingService.addToken(task.result?.token)
                    }
                startActivity<ChatsActivity>()
            } else {
                Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1
    }
}
