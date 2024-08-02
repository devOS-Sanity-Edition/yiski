package one.devos.yiski1

import dev.minn.jda.ktx.coroutines.await
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import one.devos.yiski1.tables.moderation.InfractionMessage

suspend fun TextChannel.messagesAsInfractions(count: Int, authorId: Long): List<String> {
    val messageHistory = this.getHistoryBefore(this.latestMessageIdLong, count).await().retrievedHistory.filter { it.author.idLong == authorId }

    return messageHistory.map {
        Json.encodeToString(
            InfractionMessage.serializer(),
            InfractionMessage(
                it.author.idLong,
                it.author.asTag,
                it.idLong,
                it.contentRaw,
                it.timeCreated.toEpochSecond()
            )
        )
    }
}