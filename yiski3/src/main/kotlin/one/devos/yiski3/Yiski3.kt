package one.devos.yiski3

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiConstants
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski3.data.Yiski3ConfigData
import org.kohsuke.github.GitHubBuilder
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@YiskiModule
class Yiski3(
    // Change these to vals if they're needed!
    database: DatabaseManager,
    aviation: Aviation,
    jda: JDA,
    private val config: Yiski3ConfigData
) : YiskiModuleEntrypoint(
    database,
    aviation,
    jda,
    config
) {

    companion object {
        lateinit var instance: Yiski3
            private set

        val config: Yiski3ConfigData
            get() = instance.config

        val gitHub = GitHubBuilder().withOAuthToken(YiskiConstants.config.universal.githubToken).build()
    }

    init {
        instance = this
    }

    override fun setup() {
        logger.info { "Yiski3 module loaded." }
    }

}
