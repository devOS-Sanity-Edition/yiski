package one.devos.yiski.runner

import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.events.*
import xyz.artrinix.aviation.internal.utils.on

object AviationEventHandler {

    suspend fun setupEvents(aviation: Aviation) {
        aviation.on<AviationExceptionEvent> {
            logger.error(this.error){"Aviation threw an exception"}
        }

        aviation.on<BadArgumentEvent> {
            logger.error(this.error){"[Command Execution] Bad Argument Event"}
            this.ctx.send("An unexpected error has occurred, please report this to the Artrinix Discord")
        }

        aviation.on<BadEnvironmentEvent> {
            this.ctx.send("An unexpected error has occurred, please report this to the Artrinix Discord")
        }

        aviation.on<CommandExecutedEvent> {
            logger.info(){"[Command Execution] ${this.ctx.author.asTag} (${this.ctx.author.id}) executed ${this.command.name} with trigger ${this.ctx.trigger}"}
        }

        aviation.on<CommandFailedEvent> {
            logger.error( this.error){"[Command Execution] Command Failed Event"}
            this.ctx.send("An unexpected error has occurred, please report this to the Artrinix Discord")
        }

        aviation.on<CommandInvokedEvent> {
            logger.info(){"[Command Invoke] ${this.ctx.author.asTag} (${this.ctx.author.id}) invoked ${this.command.name} with trigger ${this.ctx.trigger}"}
        }

        aviation.on<CommandRateLimitedEvent> {

        }

        aviation.on<MissingPermissionsEvent> {
            this.ctx.send("You don't have permission to do that.")
        }

        aviation.on<ParsingErrorEvent> {
            logger.error(this.error){"[Command Execution] Parsing Error Event"}
            this.ctx.send("An unexpected error has occurred, please report this to the Artrinix Discord")
        }

        aviation.on<UnknownCommandEvent> {

        }

        aviation.on<UnknownSlashCommandEvent> {

        }

        aviation.on<UnknownSlashSubCommandEvent> {

        }

        aviation.on<UnknownSlashSubCommandGroupEvent> {

        }

    }
}