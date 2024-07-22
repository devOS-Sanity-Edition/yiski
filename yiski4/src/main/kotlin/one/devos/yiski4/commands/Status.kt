package one.devos.yiski4.commands

import one.devos.yiski.common.data.Colors
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.awt.Color
import kotlin.random.Random

class Status : Scaffold {
    @SlashCommand("status", description = "Displays the system status of an agent")
    suspend fun status(ctx: SlashContext) {
        val unimpl = "-# [EXTREMELY LOUD INCORRECT BUZZER SOUND]"
        val funniFooters: List<String> = listOf( "hihi", "uwu", "owo", "wazbewwy pwi fwour?? uwu?", "-w-", "hwody fwen!", "fern!- i mean frewn!", "meep", "hihi again!", "hai!")
        val funniFootersRandom = funniFooters.random()

        ctx.sendEmbed {
            setColor(Color(Colors.INFO.r, Colors.INFO.g, Colors.INFO.b))
            setTitle("Agent Name: $unimpl")
            addField("$unimpl Hardware",
                """
                    Model: 
                    $unimpl
                    
                    Processor: 
                    $unimpl
                    
                    Revision: 
                    $unimpl
                    
                    Bootloader date: 
                    $unimpl
                """.trimIndent(), false)
            addField("", "-# $funniFootersRandom", false)
        }
    }
}