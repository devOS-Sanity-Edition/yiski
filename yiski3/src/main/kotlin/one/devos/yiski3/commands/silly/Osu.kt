package one.devos.yiski3.commands.silly

import com.oopsjpeg.osu4j.GameMode
import com.oopsjpeg.osu4j.OsuBeatmap
import com.oopsjpeg.osu4j.OsuScore
import com.oopsjpeg.osu4j.backend.EndpointBeatmaps
import com.oopsjpeg.osu4j.backend.EndpointUsers
import com.oopsjpeg.osu4j.backend.Osu
import com.oopsjpeg.osu4j.exception.OsuAPIException
import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski.common.utils.EmbedHelpers.videoUpload
import one.devos.yiski.common.utils.toTimeString
import one.devos.yiski3.Yiski3
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold
import kotlin.time.Duration.Companion.seconds


@YiskiModule
@SlashCommand(name = "osu", description = "View some basic osu! stats")
class Osu : Scaffold {
    @SlashSubCommand("View information about an osu! player on Standard mode")
    suspend fun standard(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = try {
            osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.STANDARD).build())
        } catch (e: java.lang.UnsupportedOperationException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
            ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
            return
        }
        val top3scores: List<OsuScore> = user.getTopScores(3).get()

        try {
            response.editOriginalEmbeds(Embed {
                title = "osu! data for ${user.username} - ${user.country} - ${user.id} - osu! mode"
                description = "[Click here to visit their profile](${user.url})"
                field("Rank", "#${"%,d".format(user.rank)}", true)
                field("Country Rank", "#${"%,d".format(user.countryRank)}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", "${user.accuracy}%", true)
                field("Play Count", "%,d".format(user.playCount), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", "%,d".format(user.totalHits), true)
                field("PP (performance points)", "%,d".format(user.pp), true)
                field("", "", true)
                field(
                    "SS-A Scores", """
                    SSH: ${"%,d".format(user.countRankSSH)}
                    SS: ${"%,d".format(user.countRankSS)}
                    SH: ${"%,d".format(user.countRankSH)}
                    S: ${"%,d".format(user.countRankS)}
                    A: ${"%,d".format(user.countRankA)}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent()
                )

                for (i in 0 until top3scores.size) {
                    val score: OsuScore = top3scores[i]
                    val beatmap: OsuBeatmap = score.beatmap.get()
                    field("Top score #${i + 1}", "${beatmap.title} by ${beatmap.artist}, mapped by ${beatmap.creatorName} - ${beatmap.id} - [Link to beatmap](${beatmap.url})", false)
                }

                color = EmbedHelpers.infoColor()
                thumbnail = "https://a.ppy.sh/${user.id}"
                footer("Note that top scores on this are for osu!stable and not osu!lazer. Everything else is both. osu!lazer scores will be grabbed once osuv2kt is done.")

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
    suspend fun taiko(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = try {
            osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.TAIKO).build())
        } catch (e: java.lang.UnsupportedOperationException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
            ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
            return
        }

        val top3scores: List<OsuScore> = user.getTopScores(3).get()

        try {
            response.editOriginalEmbeds(Embed {
                title = "osu! data for ${user.username} - ${user.country} - ${user.id} - osu!taiko mode"
                description = "[Click here to visit their profile](${user.url})"
                field("Rank", "#${"%,d".format(user.rank)}", true)
                field("Country Rank", "#${"%,d".format(user.countryRank)}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", "${user.accuracy}%", true)
                field("Play Count", "%,d".format(user.playCount), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", "%,d".format(user.totalHits), true)
                field("PP (performance points)", "%,d".format(user.pp), true)
                field("", "", true)
                field(
                    "SS-A Scores", """
                    SSH: ${"%,d".format(user.countRankSSH)}
                    SS: ${"%,d".format(user.countRankSS)}
                    SH: ${"%,d".format(user.countRankSH)}
                    S: ${"%,d".format(user.countRankS)}
                    A: ${"%,d".format(user.countRankA)}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent()
                )

                for (i in 0 until top3scores.size) {
                    val score: OsuScore = top3scores[i]
                    val beatmap: OsuBeatmap = score.beatmap.get()
                    field("Top score #${i + 1}", "${beatmap.title} by ${beatmap.artist}, mapped by ${beatmap.creatorName} - ${beatmap.id} - [Link to beatmap](${beatmap.url})", false)
                }

                color = EmbedHelpers.infoColor()
                thumbnail = "https://a.ppy.sh/${user.id}"
                footer("Note that top scores on this are for osu!stable and not osu!lazer. Everything else is both. osu!lazer scores will be grabbed once osuv2kt is done.")
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

    @SlashSubCommand("View information about an osu! player on Mania mode")
    suspend fun mania(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = try {
            osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.MANIA).build())
        } catch (e: java.lang.UnsupportedOperationException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
            ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
            return
        }

        val top3scores: List<OsuScore> = user.getTopScores(3).get()

        try {
            response.editOriginalEmbeds(Embed {
                title = "osu! data for ${user.username} - ${user.country} - ${user.id} - osu!mania mode"
                description = "[Click here to visit their profile](${user.url})"
                field("Rank", "#${"%,d".format(user.rank)}", true)
                field("Country Rank", "#${"%,d".format(user.countryRank)}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", "${user.accuracy}%", true)
                field("Play Count", "%,d".format(user.playCount), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", "%,d".format(user.totalHits), true)
                field("PP (performance points)", "%,d".format(user.pp), true)
                field("", "", true)
                field(
                    "SS-A Scores", """
                    SSH: ${"%,d".format(user.countRankSSH)}
                    SS: ${"%,d".format(user.countRankSS)}
                    SH: ${"%,d".format(user.countRankSH)}
                    S: ${"%,d".format(user.countRankS)}
                    A: ${"%,d".format(user.countRankA)}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent())

                for (i in 0 until top3scores.size) {
                    val score: OsuScore = top3scores[i]
                    val beatmap: OsuBeatmap = score.beatmap.get()
                    field("Top score #${i + 1}", "${beatmap.title} by ${beatmap.artist}, mapped by ${beatmap.creatorName} - ${beatmap.id} - [Link to beatmap](${beatmap.url})", false)
                }

                color = EmbedHelpers.infoColor()
                thumbnail = "https://a.ppy.sh/${user.id}"
                footer("Note that top scores on this are for osu!stable and not osu!lazer. Everything else is both. osu!lazer scores will be grabbed once osuv2kt is done.")
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

    @SlashSubCommand("View information about an osu! player on Catch mode")
    suspend fun catch(ctx: SlashContext, @Description("Username of said osu! player") player: String) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val user = try {
            osu.users.query(EndpointUsers.ArgumentsBuilder(player).setMode(GameMode.CATCH_THE_BEAT).build())
        } catch (e: java.lang.UnsupportedOperationException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
            ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
            return
        }

        val top3scores: List<OsuScore> = user.getTopScores(3).get()

        try {
            response.editOriginalEmbeds(Embed {
                title = "osu! data for ${user.username} - ${user.country} - ${user.id} - osu!catch mode"
                description = "[Click here to visit their profile](${user.url})"
                field("Rank", "#${"%,d".format(user.rank)}", true)
                field("Country Rank", "#${"%,d".format(user.countryRank)}", true)
                field("Ranked Score", "%,d".format(user.rankedScore), true)
                field("Hit Accuracy", "${user.accuracy}%", true)
                field("Play Count", "%,d".format(user.playCount), true)
                field("Total Score", "%,d".format(user.totalScore), true)
                field("Total Hits", "%,d".format(user.totalHits), true)
                field("PP (performance points)", "%,d".format(user.pp), true)
                field("", "", true)
                field(
                    "SS-A Scores", """
                    SSH: ${"%,d".format(user.countRankSSH)}
                    SS: ${"%,d".format(user.countRankSS)}
                    SH: ${"%,d".format(user.countRankSH)}
                    S: ${"%,d".format(user.countRankS)}
                    A: ${"%,d".format(user.countRankA)}
                    
                    `*H - Hidden/Flashlight Mod`
                """.trimIndent())

                for (i in 0 until top3scores.size) {
                    val score: OsuScore = top3scores[i]
                    val beatmap: OsuBeatmap = score.beatmap.get()
                    field("Top score #${i + 1}", "${beatmap.title} by ${beatmap.artist}, mapped by ${beatmap.creatorName} - ${beatmap.id} - [Link to beatmap](${beatmap.url})", false)
                }

                color = EmbedHelpers.infoColor()
                thumbnail = "https://a.ppy.sh/${user.id}"
                footer("Note that top scores on this are for osu!stable and not osu!lazer. Everything else is both. osu!lazer scores will be grabbed once osuv2kt is done.")
            }).await()
        } catch (e: OsuAPIException) {
            response.editOriginalEmbeds(Embed {
                title = "ruh roh"
                description = "Failed to get osu! data for this player, ${e.message}"
                color = EmbedHelpers.failColor()
            }).await()
            ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
            return
        }
    }

    @SlashSubCommand("View info on a beatmap")
    suspend fun beatmap(ctx: SlashContext, @Description("View info about a beatmap") beatmapid: Int) {
        val response = ctx.interaction.deferReply().await()

        val osu: Osu = Osu.getAPI(Yiski3.config.apiKeys.osu)
        val beatmap = try {
            osu.beatmaps.getAsQuery(
                EndpointBeatmaps.ArgumentsBuilder()
                    .setBeatmapID(beatmapid)
                    .build())
                .resolve()[0]
        } catch (e: Exception) {
            if (e.message == "Index 0 out of bounds for length 0") {
                response.editOriginalEmbeds(Embed {
                    title = "ruh roh"
                    description = "Failed to get osu! data for this beatmap, ${e.message}, aka, you didn't put in a Beatmap ID."
                    color = EmbedHelpers.failColor()
                }).await()
                ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
            } else {
                response.editOriginalEmbeds(Embed {
                    title = "ruh roh"
                    description = "Failed to get osu! data for this beatmap, ${e.message}"
                    color = EmbedHelpers.failColor()
                }).await()
                ctx.channel.sendFiles(videoUpload(Yiski3.config.videos.osufailscreen)).await()
            }
            return
        }

        val beatmapTagsCount = beatmap.tags.size

        response.editOriginalEmbeds(Embed {
            title = "osu! data for beatmap ${beatmap.title} by ${beatmap.artist} - ${beatmap.id}"
            description = "[Link to beatmap](${beatmap.url})"
            field("Mapper", beatmap.creatorName, false)
            field("Submission date", beatmap.approvedDate.toLocalDateTime().toString(), false)
            field("Language", beatmap.language.getName(), false)
            field("Genre", beatmap.genre.getName(), false)
            field("Hit Length / Total Length", "${beatmap.hitLength.seconds.toTimeString()} / ${beatmap.totalLength.seconds.toTimeString()}", true)
            field("BPM", beatmap.bpm.toString(), true)
            field("HP Drain", beatmap.drain.toString(), true)
            field("Accuracy", beatmap.overall.toString(), true)
            field("Pass Count", beatmap.passCount.toString(), true)
            field("Starting Difficulty", beatmap.difficulty.toString(), true)
            field("Tags [first 10]", beatmap.tags.take(10).joinToString(), false)
        }).await()
    }
}