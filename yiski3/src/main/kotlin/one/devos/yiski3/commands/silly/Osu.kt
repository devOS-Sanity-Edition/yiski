package one.devos.yiski3.commands.silly

import com.oopsjpeg.osu4j.GameMode
import com.oopsjpeg.osu4j.backend.EndpointUsers
import com.oopsjpeg.osu4j.backend.Osu
import com.oopsjpeg.osu4j.exception.OsuAPIException
import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski.common.utils.EmbedHelpers.videoUpload
import one.devos.yiski3.Yiski3
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold

@YiskiModule
@SlashCommand(name = "osu", description = "View some basic osu! stats")
class Osu : Scaffold {
    @SlashSubCommand("View information about an osu! player on Standard mode")
    suspend fun playerStandard(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.STANDARD).build())

        try {
            response.editOriginalEmbeds(Embed {
                title = "Osu! data for ${user.username} - ${user.id} - osu! mode"
                description = user.url.toString()
                field("Rank", "#${user.rank}", true)
                field("Country Rank", "#${user.countryRank}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", user.accuracy.toString(), true)
                field("Play Count", user.playCount.toString(), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", user.totalHits.toString(), true)
                field("PP (performance points)", user.pp.toString(), true)
                field(
                    "SS-A Scores", """
                    SSH: ${user.countRankSSH}
                    SS: ${user.countRankSS}
                    SH: ${user.countRankSH}
                    S: ${user.countRankS}
                    A: ${user.countRankA}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent()
                )
                color = EmbedHelpers.infoColor()
            }).await()
        } catch (e: OsuAPIException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
            ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
        }
    }

    @SlashSubCommand("View information about an osu! player on Taiko mode")
    suspend fun playerTaiko(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.TAIKO).build())

        try {
            response.editOriginalEmbeds(Embed {
                title = "Osu! data for ${user.username} - ${user.id} - osu!taiko mode"
                description = user.url.toString()
                field("Rank", "#${user.rank}", true)
                field("Country Rank", "#${user.countryRank}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", user.accuracy.toString(), true)
                field("Play Count", user.playCount.toString(), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", user.totalHits.toString(), true)
                field("PP (performance points)", user.pp.toString(), true)
                field(
                    "SS-A Scores", """
                    SSH: ${user.countRankSSH}
                    SS: ${user.countRankSS}
                    SH: ${user.countRankSH}
                    S: ${user.countRankS}
                    A: ${user.countRankA}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent()
                )
                color = EmbedHelpers.infoColor()
            }).await()
        } catch (e: OsuAPIException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
        }
    }

    @SlashSubCommand("View information about an osu! player on Mania mode")
    suspend fun playerMania(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.MANIA).build())

        try {
            response.editOriginalEmbeds(Embed {
                title = "Osu! data for ${user.username} - ${user.id} - osu!mania mode"
                description = user.url.toString()
                field("Rank", "#${user.rank}", true)
                field("Country Rank", "#${user.countryRank}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", user.accuracy.toString(), true)
                field("Play Count", user.playCount.toString(), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", user.totalHits.toString(), true)
                field("PP (performance points)", user.pp.toString(), true)
                field(
                    "SS-A Scores", """
                    SSH: ${user.countRankSSH}
                    SS: ${user.countRankSS}
                    SH: ${user.countRankSH}
                    S: ${user.countRankS}
                    A: ${user.countRankA}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent()
                )
                color = EmbedHelpers.infoColor()
            }).await()
        } catch (e: OsuAPIException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
        }
    }

    @SlashSubCommand("View information about an osu! player on Catch mode")
    suspend fun playerCatch(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.CATCH_THE_BEAT).build())

        try {
            response.editOriginalEmbeds(Embed {
                title = "Osu! data for ${user.username} - ${user.id} - osu!catch mode"
                description = user.url.toString()
                field("Rank", "#${user.rank}", true)
                field("Country Rank", "#${user.countryRank}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", user.accuracy.toString(), true)
                field("Play Count", user.playCount.toString(), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", user.totalHits.toString(), true)
                field("PP (performance points)", user.pp.toString(), true)
                field(
                    "SS-A Scores", """
                    SSH: ${user.countRankSSH}
                    SS: ${user.countRankSS}
                    SH: ${user.countRankSH}
                    S: ${user.countRankS}
                    A: ${user.countRankA}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent()
                )
                color = EmbedHelpers.infoColor()
            }).await()
        } catch (e: OsuAPIException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
        }
    }
}