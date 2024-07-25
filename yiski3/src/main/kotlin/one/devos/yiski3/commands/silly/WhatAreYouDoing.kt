package one.devos.yiski3.commands.silly

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski3.Yiski3
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class WhatAreYouDoing : Scaffold {
    @SlashCommand(name = "whatareyoudoing", description = "STOP PLEASE STOP FOR THE LO-")
    suspend fun whatareyoudoing(ctx: SlashContext) {
        val whatAreYouDoing = EmbedHelpers.imagesPath(Yiski3.config.videos.whatareyoudoing)

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "NOT AGAIN"
                description = "STOP PLEASE STOP FOR THE LO-"
                color = EmbedHelpers.infoColor()
            }).await()
        ctx.channel.sendFiles(FileUpload.fromData(whatAreYouDoing, Yiski3.config.videos.whatareyoudoingfile)).await()
    }
}