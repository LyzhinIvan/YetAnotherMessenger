package ilyzhin.yetanothermessenger

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ilyzhin.yetanothermessenger.models.Chat
import ilyzhin.yetanothermessenger.models.User
import kotlinx.android.synthetic.main.activity_chats.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity

class ChatsActivity : AppCompatActivity() {

    private var currentUser = FirebaseAuth.getInstance().currentUser!!
    private lateinit var adapter : ChatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        Toast.makeText(this, "Hello ${currentUser.displayName}", Toast.LENGTH_SHORT).show()

        initRecycler()
        loadChats()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.settingsItem-> {
                startActivity<SettingsActivity>()
                true
            }
            R.id.logoutItem-> {
                AuthUI.getInstance().signOut(this).addOnCompleteListener {
                    startActivity(intentFor<SignInActivity>().clearTask().newTask())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecycler() {
        adapter = ChatsAdapter(this)
        rvChats.adapter = adapter
        rvChats.layoutManager = LinearLayoutManager(this)
    }

    private fun loadChats() {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { userDoc ->
                val user = userDoc.toObject(User::class.java)!!
                val chats = mutableListOf<Chat>()
                adapter.setChats(chats)
                user.chats.forEach {chatId ->
                    FirebaseFirestore.getInstance().collection("chats").document(chatId).get()
                        .addOnSuccessListener {
                            Log.d("MYAPP", it.data.toString())
                            chats.add(it.toObject(Chat::class.java)!!.withId(it.id))
                            adapter.notifyItemInserted(chats.size - 1)
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed loading chats", Toast.LENGTH_SHORT).show()
            }
    }

}
