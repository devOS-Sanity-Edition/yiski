package one.devos.yiski1.commands.development

import kotlinx.coroutines.future.await as CoroutineAwait
import dev.minn.jda.ktx.coroutines.await
import xyz.artrinix.aviation.command.message.annotations.Greedy
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.util.concurrent.CompletableFuture
import javax.script.ScriptEngineManager
import javax.script.ScriptException

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

        @Suppress("TooGenericExceptionCaught")
        try {
            @Suppress("MaxLineLength")
            val result = engine.eval("@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class, kotlinx.coroutines.DelicateCoroutinesApi::class)\nkotlinx.coroutines.runBlocking { $stripped }", engine.createBindings().apply { bindings.forEach(::put) })

            when (result) {
                null -> {
                    ctx.send("Script completed but did not return anything.")
                }
                is CompletableFuture<*> -> {
                    val m = ctx.interaction
                        .deferReply()
                        .setContent("```\nCompletableFuture<Pending>```")
                        .await()
                    val post = result.CoroutineAwait()
                    m.editOriginal("```kotlin\n$post\n```")
                }
                else -> {
                    ctx.send("```kotlin\n${result.toString().take(1950)}\n```")
                }
            }
        } catch (scriptException: ScriptException) {
            ctx.sendPrivate("Invalid script provided!\n```kotlin\n${scriptException.localizedMessage}\n```")
        } catch (exception: Exception) {
            ctx.sendPrivate("An exception occurred.\n```kotlin\n${exception.localizedMessage}\n```")
        }
    }
}