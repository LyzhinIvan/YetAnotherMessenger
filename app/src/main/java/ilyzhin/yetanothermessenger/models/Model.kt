package ilyzhin.yetanothermessenger.models

import androidx.annotation.NonNull
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
abstract class Model(@Exclude var id : String = "") {

    fun <T : Model> withId(@NonNull id : String) : T {
        this.id = id
        return this as T
    }

    override fun equals(other: Any?): Boolean {
        other.let {
            if (other is Model && other.id.isNotEmpty() && this.id.isNotEmpty() && other.id == this.id) {
                return true
            }
        }
        return super.equals(other)
    }
}