package one.devos.yiski3.commands

import dev.minn.jda.ktx.messages.Embed
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski3.Yiski3
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold

@YiskiModule
@SlashCommand(name = "gh", description = "GitHub related commands")
class GitHub : Scaffold {
    @SlashSubCommand("Lists information about a supplied repo")
    suspend fun repo(ctx: SlashContext, @Description("Supply a username or organization") username: String, @Description("Supply a repository") repo: String) {
        val getRepo = Yiski3.gitHub.getRepository("$username/$repo")

        fun getLicense(): String {  // before you ask, no, a `getRepo.language.name ?: "Cannot get license"` would not have worked,
                                    // since it was erroring at getRepo.language itself.
            return if (getRepo.license != null) {
                getRepo.license.name
            } else {
                "Cannot get license."
            }
        }



        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = getRepo.name
                description = getRepo.description
                field("Language", getRepo.language ?: "Cannot get language.", true)   // find a way to make it so if `getRepo.language` returns a null,
                                                                        // it just spits out "Language not found", i already tried with an `?:` and it didnt work
                                                                        //
                                                                        // welp, as of 21:21 on jul 25th 2024: i was laying in bed and i got the thought of a probable solution, behold..
                                                                        // it works.. using a function lmao, for license, since that was throwing null errors. keep same solution for language.
                field("License", getLicense(), true)        // <-- stupid problem child >:(
                field("Stars", getRepo.stargazersCount.toString(), true)
                field("Default Branch", getRepo.defaultBranch)
            }).queue()
    }
}