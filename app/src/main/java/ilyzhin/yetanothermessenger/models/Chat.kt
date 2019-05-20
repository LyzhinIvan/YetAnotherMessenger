package ilyzhin.yetanothermessenger.models

data class Chat(var title: String = "",
                var photoId: String? = null,
                var users: MutableList<String> = mutableListOf()) : Model()