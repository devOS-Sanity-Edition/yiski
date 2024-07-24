package one.devos.yiski1.commands.tools

import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Help : Scaffold {
    @SlashCommand("help", description = "Displays a help message with available commands")
    suspend fun help(ctx: SlashContext) {
        ctx.sendEmbed {
            setTitle("Help")
            setDescription("Here's a list of commands")

            addField("todo", "add commands", true)
        }
    }
}