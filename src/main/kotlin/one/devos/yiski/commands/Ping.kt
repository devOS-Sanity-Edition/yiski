package one.devos.yiski.commands

import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Ping : Scaffold {
    @SlashCommand("ping", "Ping command")
    suspend fun ping(ctx: SlashContext) {
        ctx.sendEmbed {
            setTitle("Pong!")
        }
    }
}