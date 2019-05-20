package ilyzhin.yetanothermessenger

import Constants
import Constants.RC_SELECT_IMAGE
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage
import ilyzhin.yetanothermessenger.glide.GlideApp
import ilyzhin.yetanothermessenger.helpers.FirebaseHelper
import kotlinx.android.synthetic.main.activity_new_chat.*
import java.io.ByteArrayOutputStream
import java.util.*


class NewChatActivity : AppCompatActivity() {

    private var isPhotoSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat)

        btnCreate.setOnClickListener {
            val chatTitle = etChatTitle.text.toString()
            if (chatTitle.isBlank()) return@setOnClickListener

            val photoId = if (isPhotoSelected) UUID.randomUUID().toString() else null
            FirebaseHelper.createChat(chatTitle, photoId)
                .addOnSuccessListener {chatId->
                    if (isPhotoSelected) {
                        val bitmap = (ivPhoto.drawable as BitmapDrawable).bitmap
                        val baos = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        val bytes = baos.toByteArray()
                        FirebaseStorage.getInstance().getReference("chat_avatars/$photoId")
                            .putBytes(bytes)
                    }
                    val intent = Intent(this, MessagesActivity::class.java)
                    intent.putExtra(Constants.CHAT_ID, chatId)
                    intent.putExtra(Constants.CHAT_TITLE, chatTitle)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    ContextCompat.startActivity(this, intent, Bundle.EMPTY)
                    finish()
                }
        }

        ivPhoto.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            startActivityForResult(Intent.createChooser(intent, "Select Chat Photo"), RC_SELECT_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                GlideApp.with(this).load(bitmap).placeholder(R.drawable.default_chat_icon).into(ivPhoto)
                isPhotoSelected = true
            }
        }
    }
}
