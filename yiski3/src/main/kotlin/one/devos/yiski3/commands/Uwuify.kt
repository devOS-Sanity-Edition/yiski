package one.devos.yiski3.commands

import one.devos.yiski3.utils.uwuify as uwuifyHelper
import xyz.artrinix.aviation.command.message.annotations.Greedy
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Uwuify : Scaffold {
    @SlashCommand(name = "uwuify", description = "uwu~ i wike your text~")
    suspend fun uwuify(ctx: SlashContext, @Greedy @Description("What text do you want to uwuify~") input: String) {
        val result = uwuifyHelper(input)
        ctx.sendEmbed {
            setTitle("Uwuifier")
            addField("Input: `$input`", "Result: `${result}`", false)
        }
    }
}