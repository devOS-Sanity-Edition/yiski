package one.devos.yiski4.commands

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.data.Colors
import one.devos.yiski.common.utils.RandomTextList
import one.devos.yiski4.Yiski4
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.awt.Color

class Status : Scaffold {
    @OptIn(YiskiModule::class)
    @SlashCommand("status", description = "Displays the system status of an agent")
    suspend fun status(ctx: SlashContext) {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val response: HttpResponse = client.get(Yiski4.config.agent.ip.toString())
        val unimpl = "[EXTREMELY LOUD INCORRECT BUZZER SOUND]"

        ctx.sendEmbed {
            setColor(Color(Colors.INFO.r, Colors.INFO.g, Colors.INFO.b))
            setTitle("Agent Name: $unimpl")
            if (Yiski4.config.agent.isRPi) {
                addField(
                    "$unimpl Hardware",
                    """
                    Model: 
                    $unimpl
                    
                    Processor: 
                    $unimpl
                    
                    Revision: 
                    $unimpl
                    
                    Bootloader date: 
                    $unimpl
                """.trimIndent(), false
                )
                addField("", "-# ${RandomTextList.sillyFooters()}", false)
            }

        }
    }
}