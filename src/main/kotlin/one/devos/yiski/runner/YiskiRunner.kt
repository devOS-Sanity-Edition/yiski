package one.devos.yiski.runner

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
import one.devos.yiski.common.YiskiConstants
import one.devos.yiski.common.YiskiConstants.database
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.utils.getConfigSetupEntrypoint
import one.devos.yiski.common.utils.getMainEntrypoint
import one.devos.yiski.module.loader.impl.discovery.ClasspathModuleDiscoverer
import one.devos.yiski.module.loader.impl.discovery.JarModuleDiscoverer
import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.AviationBuilder
import xyz.artrinix.aviation.ratelimit.DefaultRateLimitStrategy
import kotlin.io.path.Path

val logger = KotlinLogging.logger { }

@YiskiModule
object YiskiRunner {
    lateinit var jda: JDA
    lateinit var aviation: Aviation

    @JvmStatic
    fun main(args: Array<String>): Unit = runBlocking {
        logger.info { "Starting up Yiski" }
        logger.info { "Version: ${YiskiConstants.version}" }
        logger.info { "Aviation Version: ${YiskiConstants.aviationVersion}" }
        logger.info { "JDA Version: ${YiskiConstants.jdaVersion}" }

        logger.info { "Detecting modules..."}
        YiskiConstants.moduleLoader.addDiscoverer(ClasspathModuleDiscoverer())
        YiskiConstants.moduleLoader.addDiscoverer(JarModuleDiscoverer(Path("modules")))
        val modules = YiskiConstants.moduleLoader.discover()
        val configEntrypoints = modules.associateWith(YiskiConstants.moduleLoader::getConfigSetupEntrypoint)
        configEntrypoints.forEach { (metadata, entrypoint) ->
            entrypoint?.read() ?: logger.warn { "No config setup entrypoint found for module ${metadata.information.id} (${metadata.information.name})." }
        }

        logger.info { "Found ${modules.count()} modules." }
        modules.forEach { module ->
            module.module.packages?.let { packages ->
                if (packages.databasePackage.isEmpty()) {
                    return@forEach
                }

                database.registerTables(packages.databasePackage)
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
                AviationEventHandler.setupEvents(this)
                modules.forEach { module ->
                    module.module.packages?.let { packages ->
                        val slashCommandsPackage = packages.slashCommandsPackage
                        if (slashCommandsPackage.isEmpty()) {
                            return@forEach
                        }

                        this@apply.slashCommands.register(slashCommandsPackage)
                    }
                }
            }

        jda.addEventListener(aviation)

        jda.listener<ReadyEvent> {
            try {
                logger.info { "Yiski started!" }
//                aviation.syncCommands(jda)
                aviation.syncCommandsForTestGuilds(jda)
            } catch (e: Exception) {
                logger.error { "Something has gone very wrong with the Ready Event." }
            }
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

        modules.mapNotNull { metadata ->
            val config = configEntrypoints.firstNotNullOf { (configMetadata, configModule) ->
                if (configMetadata.information.id == metadata.information.id) configModule else null
            }.config
            YiskiConstants.moduleLoader.getMainEntrypoint(metadata, database, aviation, jda, config)
        }.forEach(YiskiModuleEntrypoint::setup)
    }

}
