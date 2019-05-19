package ilyzhin.yetanothermessenger.models


data class User(var name: String = "",
                var photoId: String? = null,
                var chats : MutableList<String> = mutableListOf(),
                var tokens : MutableList<String> = mutableListOf()) : Model()