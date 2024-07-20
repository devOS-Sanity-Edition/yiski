package one.devos.yiski1.commands.tools

import one.devos.yiski.common.YiskiConstants
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class About : Scaffold {
    @SlashCommand("about", "About the Yiski bot")
    suspend fun about(ctx: SlashContext) {
        val user = ctx.jda.getUserById(942533628435501076)
        val user2 = ctx.jda.getUserById(299779734932291604)
        ctx.sendEmbed {
            setThumbnail(ctx.jda.selfUser.effectiveAvatarUrl)
            setTitle("Yiski, an in-house devOS: Sanity Edition bot")
            setDescription("Yiski is an in-house bot series started by asojidev, original founder of devOS: Sanity Edition 1.0 and 2.0. The original Yiski bot was created in response of the abandonment of the original in-house bot, devOS Services, due to lack of care from its original creator. The original bot was created in Python using `pycord`, and after many many many attempts at a rewrite of original Yiski, this attempt comes.")
            addField("Yiski Version", "`${YiskiConstants.version}`", true)
            addField("Aviation Version", "`${YiskiConstants.aviationVersion}`", true)
            addField("JDA Version", "`${YiskiConstants.jdaVersion}`", true)
            setFooter("Built by ${user?.asTag ?: "asojidev"}, assisted by Aviation's creator, ${user2?.asTag ?: "CephalonCosmic"}", user?.effectiveAvatarUrl)
        }
    }
}