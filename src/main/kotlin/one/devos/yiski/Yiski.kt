package one.devos.yiski

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.jdabuilder.default
import dev.minn.jda.ktx.jdabuilder.intents
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.events.session.ShutdownEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import org.jetbrains.exposed.sql.Database
import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.AviationBuilder
import xyz.artrinix.aviation.events.AviationExceptionEvent
import xyz.artrinix.aviation.events.CommandFailedEvent
import xyz.artrinix.aviation.internal.utils.on
import xyz.artrinix.aviation.ratelimit.DefaultRateLimitStrategy

val logger = KotlinLogging.logger { }

object Yiski {
    val version = this::class.java.`package`.implementationVersion
    val config = Config.loadConfig()
    lateinit var jda: JDA
    lateinit var aviation: Aviation
    val client = HttpClient(CIO) { install(DefaultRequest) }
    val db by lazy { Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC") }

    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {
        logger.info { "Starting up Yiski" }

        jda = default(config.discord.botToken, enableCoroutines = true) {
            intents += listOf(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT)
        }

        aviation = AviationBuilder()
            .apply {
                ratelimitProvider = DefaultRateLimitStrategy()
                doTyping = true
                developers.addAll(config.discord.adminIDs.toTypedArray())
                registerDefaultParsers()
            }
            .build()
            .apply {
                slashCommands.register("one.devos.yiski.commands")
            }

        listenAviationEvents()

        jda.addEventListener(aviation)

        jda.listener<ReadyEvent> {
            aviation.syncCommands(jda)
            logger.info { "Yiski started!" }
        }

        jda.listener<MessageReceivedEvent> { event ->
            if (event.author.isBot || !event.isFromGuild) return@listener
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
