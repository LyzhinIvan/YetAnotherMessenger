package ilyzhin.yetanothermessenger.helpers

import com.algolia.search.saas.Client
import com.algolia.search.saas.Query
import ilyzhin.yetanothermessenger.models.Chat
import org.json.JSONObject

object AlgoliaHelper {
    private val client = Client("ZW0GGJL6SZ", "c920ab928882dacf943b254e7eaae255")
    val chatsIndex = client.getIndex("chats")

    fun addChatToIndex(chat : Chat) {
        chatsIndex.addObjectAsync(JSONObject().put("chatId", chat.id).put("title", chat.title), null)
    }

    fun searchChats(query : String, callback : (List<Chat>) -> Any){
        chatsIndex.searchAsync(Query(query).setLength(30)) { jsonObject, algoliaException ->
            if (algoliaException != null) {
                callback(emptyList())
            } else {
                val hits = jsonObject!!.getJSONArray("hits")
                val result = mutableListOf<Chat>()
                for (i in 0 until hits.length()) {
                    val hit = hits.getJSONObject(i)
                    val chatId = hit.getString("chatId")
                    val title = hit.getString("title")
                    result.add(Chat(title).withId(chatId))
                }
                callback(result)
            }
        }
    }
}