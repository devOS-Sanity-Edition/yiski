package one.devos.yiski

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.command.main
import dev.lizainslie.moeka.core.Bot
import dev.lizainslie.moeka.core.modules.AbstractModule
import dev.lizainslie.moeka.platforms.discord.Discord
import one.devos.yiski.modules.system.SystemModule
import org.reflections.Reflections
import java.nio.file.Paths


class YiskiBot : SuspendingCliktCommand() {
    override suspend fun run() {
        val bot = Bot(SystemModule)

        bot.enablePlatforms(Discord)
        bot.loadModules()

        // Dynamically pick up on modules in the classpath, this is mainly for development purposes.
        val modules = Reflections("one.devos.yiski.modules")
        modules.getSubTypesOf(AbstractModule::class.java).forEach {
            bot.modules.loadBundledModule(it?.kotlin?.objectInstance!!)
        }

        bot.init()
        bot.start()
    }
}

suspend fun main(args: Array<String>) {
    System.setProperty("moeka.bot.dir", System.getenv("WORKDIR") ?: Paths.get("").toAbsolutePath().toString())
    YiskiBot().main(args)
}