package one.devos.yiski.runner.aviation

import io.github.oshai.kotlinlogging.KotlinLogging
import one.devos.yiski.common.utils.EmbedHelpers
import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.events.*
import xyz.artrinix.aviation.internal.utils.on

object AviationEventHandler {

    private val logger = KotlinLogging.logger { }

    suspend fun setupEvents(aviation: Aviation) {
        aviation.on<AviationExceptionEvent> {
            logger.error(this.error) { "Aviation threw an exception" }
        }

        aviation.on<BadArgumentEvent> {
            logger.error(this.error) { "[Command Execution] Bad Argument Event" }
            this.ctx.sendEmbed {
                setTitle("An unexpected error has occurred, please go fuck yourself.")
                setColor(EmbedHelpers.failColor())
            }
        }

        aviation.on<BadEnvironmentEvent> {
            this.ctx.sendEmbed {
                setTitle("An unexpected error has occurred, please go fuck yourself.")
                addField("Error Type:", "Bad Environment Event", false)
                setColor(EmbedHelpers.failColor())
            }
        }

        aviation.on<CommandExecutedEvent> {
            logger.info { "[Command Execution] ${this.ctx.author.asTag} (${this.ctx.author.id}) executed ${this.command.name} with trigger ${this.ctx.trigger}" }
        }

        aviation.on<CommandFailedEvent> {
            logger.error(this.error) { "[Command Execution] Command Failed Event" }
            this.ctx.sendEmbed {
                setTitle("An unexpected error has occurred, please go fuck yourself.")
                addField("Error Type:", "Command Failed Event under Command Execution", false)
                setColor(EmbedHelpers.failColor())
            }
        }

        aviation.on<CommandInvokedEvent> {
            logger.info { "[Command Invoke] ${this.ctx.author.asTag} (${this.ctx.author.id}) invoked ${this.command.name} with trigger ${this.ctx.trigger}" }
        }

        aviation.on<CommandRateLimitedEvent> {

        }

        aviation.on<MissingPermissionsEvent> {
            this.ctx.sendEmbed {
                setTitle("Skill issue.")
                addField("Error Type:", "Missing Permissions", false)
                setColor(EmbedHelpers.warnColor())
            }
        }

        aviation.on<ParsingErrorEvent> {
            logger.error(this.error) { "[Command Execution] Parsing Error Event" }
            this.ctx.sendEmbed {
                setTitle("An unexpected error has occurred, please go fuck yourself.")
                addField("Error Type:", "Parsing Error Event under Command Execution", false)
                setColor(EmbedHelpers.failColor())
            }
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