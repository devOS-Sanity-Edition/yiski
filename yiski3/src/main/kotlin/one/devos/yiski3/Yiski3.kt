package one.devos.yiski3

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import okhttp3.OkHttpClient
import one.devos.yiski.common.YiskiConstants
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.utils.PathsHelper
import one.devos.yiski3.data.Yiski3ConfigData
import org.kohsuke.github.GitHubBuilder
import org.kohsuke.github.extras.okhttp3.OkHttpGitHubConnector
import xyz.artrinix.aviation.Aviation
import java.awt.Font
import java.io.InputStream

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

        val gitHub = GitHubBuilder().withOAuthToken(YiskiConstants.config.universal.githubToken).withConnector(OkHttpGitHubConnector(OkHttpClient())).build()

        // sorry for it being slightly cryptic with the val names but i could honestly give less than a flying fuck

        // hey future aubs, the reason why you're hardcoding it like this,instead of calling a config path, is because for some horrid reason,
        // the bot will throw a `This interaction has already been acknowledged or replied to` error if using the full enum paths helper, even tho it
        // literally makes 0 fucking sense.
        //
        // ...fucking hell.
        //
        // `val cmfis: InputStream = PathsHelper.filePath.path(PathsHelper.FileType.FONT, Yiski3.config.fonts.comicmono)` instead of the below *any* day personally.
        val cmfis: InputStream = PathsHelper.filePath("assets/fonts/comicmono.ttf")
        val cmff: Font = Font.createFont(Font.TRUETYPE_FONT, cmfis)
        val cmf = cmff.deriveFont(18.0F)
    }

    init {
        instance = this
    }

    override fun setup() {
        logger.info { "Yiski3 module loaded." }


    }

}
