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
}