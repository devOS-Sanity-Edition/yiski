package one.devos.yiski1.commands.development

import dev.minn.jda.ktx.coroutines.await
import xyz.artrinix.aviation.command.message.annotations.Greedy
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.util.concurrent.CompletableFuture
import javax.script.ScriptEngineManager
import javax.script.ScriptException
import kotlinx.coroutines.future.await as CoroutineAwait

class Evaluation : Scaffold {
    private val engine = ScriptEngineManager().getEngineByExtension("kts")

    @SlashCommand(
        "eval",
        "Evaluate code in an ephemeral runtime environment",
        guildOnly = true,
        guildId = 942513671723180034,
        developerOnly = true
    )
    suspend fun eval(ctx: SlashContext, @Greedy @Description("Script to execute") code: String) {
        val bindings = mutableMapOf(
            "ctx" to ctx,
        )

        if (engine == null) {
            ctx.sendPrivate("Failed to start the Kotlin Script Engine.")
            return
        }

        val stripped = code.replace("^```\\w+".toRegex(), "").removeSuffix("```")


        val m = ctx.interaction.deferReply().await()

        @Suppress("TooGenericExceptionCaught")
        try {

            @Suppress("MaxLineLength")
            val result = engine.eval("@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class, kotlinx.coroutines.DelicateCoroutinesApi::class)\nkotlinx.coroutines.runBlocking { $stripped }", engine.createBindings().apply { bindings.forEach(::put) })

            when (result) {
                null -> {
                    m.editOriginal("Script completed but did not return anything.").await()
                }
                is CompletableFuture<*> -> {
                    m.editOriginal("```\nCompletableFuture<Pending>```").await()

                    val post = result.CoroutineAwait()
                    m.editOriginal("```kotlin\n$post\n```").await()
                }
                else -> {
                    m.editOriginal("```kotlin\n${result.toString().take(1950)}\n```").await()
                }
            }
        } catch (scriptException: ScriptException) {
            m.setEphemeral(true).editOriginal("Invalid script provided!\n```kotlin\n${scriptException.localizedMessage}\n```").await()
        } catch (exception: Exception) {
            m.setEphemeral(true).editOriginal("An exception occurred.\n```kotlin\n${exception.localizedMessage}\n```").await()
        }
    }
}