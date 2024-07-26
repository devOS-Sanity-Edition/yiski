package one.devos.yiski5.commands

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.interactions.components.Modal
import dev.minn.jda.ktx.messages.editMessage
import kotlinx.coroutines.withTimeoutOrNull
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski5.Yiski5
import one.devos.yiski5.logger
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.time.Instant
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.minutes

@YiskiModule
class ResetVent : Scaffold {
    @SlashCommand(
        "reset-vent",
        "Resets the Vent channel",
        guildOnly = true,
        defaultUserPermissions = [Permission.ADMINISTRATOR]
    )
    suspend fun resetVent(ctx: SlashContext) {
        val now = Instant.now()

        ctx.interaction.replyModal(
            Modal("$now:reset_modal", "You are about to manually reset the vent?") {
                short("$now:reset_modal_confirmation", "Are you absolutely sure?", true, placeholder = "Yes")
            }
        ).timeout(1, TimeUnit.MINUTES).await()

        withTimeoutOrNull(1.minutes) {
            ctx.jda.listener<ModalInteractionEvent> { modal ->
                if (modal.modalId != "$now:reset_modal") return@listener
                val message = modal.deferReply(true).await()

                if (modal.getValue("$now:reset_modal_confirmation")?.asString?.lowercase() == "yes") {
                    message.editMessage(content = "Vent clearing in progress...").await()
                    logger.info { "Vent channel has been manually wiped by ${ctx.interaction.user.name} (${ctx.interaction.user.id}) at ${Yiski5.dateNow}" }

                    Yiski5.clearVentChannel(ctx.jda)

                    message.editMessage(content = "Vent cleared. \uD83D\uDE38").await()
                } else {
                    message.editMessage(content = "Vent reset canceled.").await()
                }
            }
        } ?: ctx.interaction.hook.editMessage(content = "Timed out.", components = emptyList()).await()
    }
}