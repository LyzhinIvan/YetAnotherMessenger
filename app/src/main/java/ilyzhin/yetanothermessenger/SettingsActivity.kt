package ilyzhin.yetanothermessenger

import Constants.LOG_TAG
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import ilyzhin.yetanothermessenger.glide.GlideApp
import ilyzhin.yetanothermessenger.models.User
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.util.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        private const val RC_SELECT_IMAGE = 3
    }

    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val authUser = FirebaseAuth.getInstance().currentUser!!
        etEmail.setText(authUser.email)
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(authUser.uid)
            .get()
            .addOnSuccessListener {
                user = it.toObject(User::class.java)!!.withId(it.id)
                etDisplayName.setText(user.name)
                val ref = FirebaseStorage.getInstance()
                    .getReference("avatars/${user.photoId}")

                GlideApp.with(this).load(ref).placeholder(R.drawable.default_avatar).into(ivAvatar)
            }

        ivAvatar.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            startActivityForResult(Intent.createChooser(intent, "Select Image"), RC_SELECT_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let {uri ->
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val bytes = baos.toByteArray()
                GlideApp.with(this).load(bytes).into(ivAvatar)
                val photoId = UUID.randomUUID().toString()
                val ref = FirebaseStorage
                    .getInstance()
                    .getReference("avatars/$photoId")
                ref.putBytes(bytes)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Photo saved!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Log.d(LOG_TAG, it.toString())
                    }

                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(user.id)
                    .update(mapOf("photoId" to photoId))
                    .addOnFailureListener {
                        Log.d(LOG_TAG, it.toString())
                    }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        if (!etDisplayName.text.isBlank()) {
            val newName = etDisplayName.text.toString()
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(user.id)
                .update(mapOf("name" to newName))
        }
    }
}
