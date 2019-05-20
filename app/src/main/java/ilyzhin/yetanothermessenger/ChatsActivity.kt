package ilyzhin.yetanothermessenger

import Constants.LOG_TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ilyzhin.yetanothermessenger.helpers.AlgoliaHelper
import ilyzhin.yetanothermessenger.models.Chat
import ilyzhin.yetanothermessenger.models.User
import kotlinx.android.synthetic.main.activity_chats.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity

class ChatsActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var currentUser = FirebaseAuth.getInstance().currentUser!!
    private lateinit var adapter : ChatsAdapter
    private lateinit var globalChatsAdapter : ChatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        Toast.makeText(this, "Hello ${currentUser.displayName}", Toast.LENGTH_SHORT).show()

        initRecyclers()
        loadChats()

        btnNewChat.setOnClickListener {
            startActivity<NewChatActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        ((menu!!.findItem(R.id.searchItem).actionView as SearchView)).setOnQueryTextListener(this)
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

    private fun initRecyclers() {
        adapter = ChatsAdapter(this)
        rvChats.adapter = adapter
        rvChats.layoutManager = LinearLayoutManager(this)

        globalChatsAdapter = ChatsAdapter(this)
        rvGlobalChats.adapter = globalChatsAdapter
        rvGlobalChats.layoutManager = LinearLayoutManager(this)
    }

    private fun loadChats() {
        val userRef = FirebaseFirestore.getInstance().collection("users").document(currentUser.uid)
        userRef.addSnapshotListener { userSnapshot, firebaseFirestoreException ->
            val user = userSnapshot!!.toObject(User::class.java)!!
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
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val filterText : String = newText ?: ""
        adapter.applyFilter(filterText)
        if (filterText.isBlank()) {
            tvGlobalSearch.visibility = View.GONE
            rvGlobalChats.visibility = View.GONE
        } else {
            AlgoliaHelper.searchChats(filterText) { it ->
                val currentChatIds = adapter.getChats().map { chat -> chat.id }.toSet()
                val globalChats = it.filter { chat -> chat.id !in currentChatIds }.toList()
                if (globalChats.isEmpty()) {
                    tvGlobalSearch.visibility = View.GONE
                    rvGlobalChats.visibility = View.GONE
                } else {
                    tvGlobalSearch.visibility = View.VISIBLE
                    rvGlobalChats.visibility = View.VISIBLE
                    globalChatsAdapter.setChats(globalChats)
                }
            }
        }
        return true
    }
}
