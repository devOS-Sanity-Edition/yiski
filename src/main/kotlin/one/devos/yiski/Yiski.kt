package one.devos.yiski

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.jdabuilder.default
import dev.minn.jda.ktx.jdabuilder.intents
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.events.session.ShutdownEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import one.devos.yiski.common.Config
import one.devos.yiski.common.YiskiConstants
import one.devos.yiski.common.YiskiModuleEntrypoint
import one.devos.yiski.common.utils.ModulesDetection.detectModules
import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.AviationBuilder
import xyz.artrinix.aviation.events.AviationExceptionEvent
import xyz.artrinix.aviation.events.CommandFailedEvent
import xyz.artrinix.aviation.internal.utils.on
import xyz.artrinix.aviation.ratelimit.DefaultRateLimitStrategy
import java.util.*
import kotlin.system.exitProcess

val logger = KotlinLogging.logger { }

object Yiski {
    lateinit var jda: JDA
    lateinit var aviation: Aviation

    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {
        logger.info { "Starting up Yiski" }
        logger.info { "Version: ${YiskiConstants.version}" }
        logger.info { "Aviation Version: ${YiskiConstants.aviationVersion}" }
        logger.info { "JDA Version: ${YiskiConstants.jdaVersion}" }

        logger.info { "Detecting modules..."}

        val modules = detectModules()
        modules.forEach(YiskiModuleEntrypoint::config)

        logger.info { "Found ${modules.count()} modules." }

        modules.forEach {
            try {
                logger.info { "Starting ${it.moduleName} module." }
                it.config()
            } catch (e: Exception) {
                logger.error(e) { "Failed to load ${it.moduleName} module." }
            }
        }


        jda = default(YiskiConstants.config.discord.botToken, enableCoroutines = true) {
            intents += listOf(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT)
        }

        aviation = AviationBuilder()
            .apply {
                ratelimitProvider = DefaultRateLimitStrategy()
                doTyping = true
                developers.addAll(YiskiConstants.config.discord.adminIDs.toTypedArray())
                testGuilds = mutableSetOf(YiskiConstants.config.discord.homeGuildID)
                registerDefaultParsers()
            }
            .build()
            .apply {
                modules.forEach {
                    it.aviation(this)
                }
            }

        listenAviationEvents()

        modules.forEach {
            it.jda(jda)
        }

        jda.addEventListener(aviation)

        jda.listener<ReadyEvent> {
            aviation.syncCommands(jda)
//            aviation.syncCommandsForTestGuilds(jda)
//            aviation.syncCommands(jda, YiskiConstants.config.discord.homeGuildID, true)
            logger.info { "Yiski started!" }
        }

        jda.listener<MessageReceivedEvent> { event ->
            if (event.author.isBot || !event.isFromGuild) return@listener
            if (event.message.contentRaw == "According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible. u like jazz?") {
                logger.info { "fucker-" }
                aviation.syncCommands(jda)
            }
        }

        jda.listener<ShutdownEvent> {
            logger.info { "Shutting down Yiski..." }
        }


    }



    private fun listenAviationEvents() {
        aviation.on<AviationExceptionEvent> {
            logger.error { "Oopsies. Aviation threw an error: ${this.error}" }
        }

        aviation.on<CommandFailedEvent> {
            logger.error { "[Command Execution] A command has failed: ${this.error}" }
        }
    }
}
