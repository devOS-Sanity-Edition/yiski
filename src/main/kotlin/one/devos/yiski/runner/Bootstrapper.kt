package one.devos.yiski.runner

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.jdabuilder.default
import dev.minn.jda.ktx.jdabuilder.intents
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.events.session.ShutdownEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import one.devos.yiski.common.YiskiConstants
import one.devos.yiski.common.YiskiConstants.database
import one.devos.yiski.common.YiskiShared
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.getConfigSetupEntrypoint
import one.devos.yiski.common.utils.getMainEntrypoint
import one.devos.yiski.module.loader.impl.discovery.ClasspathModuleDiscoverer
import one.devos.yiski.module.loader.impl.discovery.JarModuleDiscoverer
import one.devos.yiski.runner.aviation.AviationEventHandler
import one.devos.yiski.runner.aviation.ModuleScaffoldIndexer
import xyz.artrinix.aviation.Aviation
import xyz.artrinix.aviation.AviationBuilder
import xyz.artrinix.aviation.ratelimit.DefaultRateLimitStrategy
import kotlin.io.path.Path

@OptIn(YiskiModule::class)
internal object Bootstrapper {

    private val logger = KotlinLogging.logger("Yiski")

    lateinit var aviation: Aviation
    lateinit var jda: JDA

    @Suppress("unused")
    fun start() {
        runBlocking { run() }
    }

    suspend fun run() {
        logger.info { "Starting up Yiski" }
        logger.info { "Version: ${YiskiConstants.version}" }
        logger.info { "Aviation Version: ${YiskiConstants.aviationVersion}" }
        logger.info { "JDA Version: ${YiskiConstants.jdaVersion}" }

        val moduleLoader = YiskiShared.moduleLoader

        logger.info { "Detecting modules..."}
        moduleLoader.addDiscoverer(ClasspathModuleDiscoverer())
        moduleLoader.addDiscoverer(JarModuleDiscoverer(Path("modules")))
        val modules = moduleLoader.discover()
        val configEntrypoints = modules.associateWith(moduleLoader::getConfigSetupEntrypoint)
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
                for (scaffold in ModuleScaffoldIndexer.getScaffolds()) {
                    slashCommands.register(scaffold)
                }

                AviationEventHandler.setupEvents(this)
            }

        jda = default(YiskiConstants.config.discord.botToken, enableCoroutines = true) {
            setActivity(Activity.of(Activity.ActivityType.valueOf(YiskiConstants.config.discord.activityType.uppercase()), YiskiConstants.config.discord.activityStatus))
            intents += listOf(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.MESSAGE_CONTENT)
        }

        jda.addEventListener(aviation)

        jda.listener<ReadyEvent> {
            try {
                logger.info { "Yiski started!" }
                aviation.syncCommandsForTestGuilds(jda)
                aviation.syncCommands(jda)
            } catch (e: Exception) {
                logger.error(e) {
                    "Something has gone very wrong with the Ready Event."
                }
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

        for (entrypoint in modules.mapNotNull { metadata ->
            val config = configEntrypoints.firstNotNullOf { (configMetadata, configModule) ->
                if (configMetadata.information.id == metadata.information.id) configModule else null
            }.config
            val entrypoint = YiskiShared.moduleLoader.getMainEntrypoint(metadata, database, aviation, jda, config)
            if (entrypoint == null) {
                logger.warn { "No main setup entrypoint found for module ${metadata.information.id} (${metadata.information.name})." }
            }

            entrypoint
        }) {
            try {
                entrypoint.setup()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

}
