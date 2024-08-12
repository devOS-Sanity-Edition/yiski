package one.devos.yiski3.commands.silly

import one.devos.yiski3.utils.Zalgo.zalgolize
import org.jetbrains.kotlin.backend.common.bridges.findAllReachableDeclarations
import xyz.artrinix.aviation.command.message.annotations.Greedy
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Zalgo : Scaffold {
    @SlashCommand(name = "zalgo", description = "f̶̝͘ũ̷͈̬̫̭̅ċ̵̨̈́͘͘k̸̮̍ì̷̛̜̾n̷̬̽͆ ̶̖̉ỳ̴̨͌̐͌i̷̠͝p̷̜̥̅̈̒ĕ̶̯ḙ̸̠͔͂͋̄")
    suspend fun zalgo(ctx: SlashContext, @Greedy @Description("What text do you want to zalgoify") input: String) {
        ctx.sendEmbed {
            setTitle("Zalgo")
            addField("Input", input, false)
            addField("Result", zalgolize(input), false)
        }
    }
}