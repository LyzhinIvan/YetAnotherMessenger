package ilyzhin.yetanothermessenger.models

data class Chat(var title: String = "",
                var users: MutableList<String> = mutableListOf()) : Model()