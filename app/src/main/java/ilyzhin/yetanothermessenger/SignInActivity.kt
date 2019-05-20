package ilyzhin.yetanothermessenger

import Constants.RC_SIGN_IN
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import ilyzhin.yetanothermessenger.helpers.FirebaseHelper
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
                FirebaseHelper.onUserSignIn {
                    startActivity<ChatsActivity>()
                    finish()
                }
            } else {
                Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
