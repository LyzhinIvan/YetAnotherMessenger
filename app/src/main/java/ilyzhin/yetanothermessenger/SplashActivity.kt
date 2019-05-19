package ilyzhin.yetanothermessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ilyzhin.yetanothermessenger.helpers.FirebaseHelper
import ilyzhin.yetanothermessenger.models.Model
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        data class Entity(var title: String = "", var users : MutableList<String> = mutableListOf()) : Model() {
//            constructor() : this("")
//        }
//        FirebaseFirestore.getInstance()
//            .collection("chats")
//            .document("fTZlBm11DeGvEyvS2BI9")
//            .get()
//            .addOnSuccessListener {
//                val entity : Entity? = it.toObject(Entity::class.java)!!.withId(it.id)
//                println("id: " + entity?.id)
//                println("title: " + entity?.title)
//                println("num users: " + entity?.users?.size)
//            }

        if (FirebaseAuth.getInstance().currentUser != null) {
            FirebaseHelper.onUserSignIn {
                startActivity<ChatsActivity>()
            }
        } else {
            startActivity<SignInActivity>()
        }
    }
}
