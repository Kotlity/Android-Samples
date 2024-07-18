package example.com.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Message(
    val text: String,
    val images: List<ByteArray> = emptyList(),
    val username: String,
    val timestamp: Long,
    @SerialName("_id")
    val id: String = ObjectId().toString()
)