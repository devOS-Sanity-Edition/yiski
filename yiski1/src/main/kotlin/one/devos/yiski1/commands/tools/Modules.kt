package one.devos.yiski1.commands.tools

import one.devos.yiski.common.YiskiConstants
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Modules : Scaffold {
    @SlashCommand(name = "modules", description = "List modules loaded")
    suspend fun modules(ctx: SlashContext) {
        ctx.sendEmbed {
            setTitle("${YiskiConstants.moduleLoader.getModules().size} modules loaded")
            YiskiConstants.moduleLoader.getModules().forEach {
                addField("${it.information.name} - ${it.information.version}", it.information.description, true)
            }
        }
    }
}