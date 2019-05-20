package ilyzhin.yetanothermessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ilyzhin.yetanothermessenger.helpers.FirebaseHelper
import ilyzhin.yetanothermessenger.models.Model
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseHelper.onUserSignIn {
                startActivity<ChatsActivity>()
                finish()
            }
        } else {
            startActivity<SignInActivity>()
            finish()
        }
    }
}
