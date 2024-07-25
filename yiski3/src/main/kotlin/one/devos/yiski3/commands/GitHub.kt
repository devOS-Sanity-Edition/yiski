package one.devos.yiski3.commands

import dev.minn.jda.ktx.messages.Embed
import one.devos.yiski3.Yiski3
import org.kohsuke.github.GitHubBuilder
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold

@SlashCommand(name = "gh", description = "GitHub related commands")
class GitHub : Scaffold {
    @SlashSubCommand("Lists information about a supplied repo")
    suspend fun repo(ctx: SlashContext, @Description("Supply a username or organization") username: String, @Description("Supply a repository") repo: String) {
        val getRepo = Yiski3.gitHub.getRepository("$username/$repo")

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = getRepo.name
                description = getRepo.description
                field("Language", getRepo.language, true)   // find a way to make it so if `getRepo.language` returns a null,
                                                                        // it just spits out "Language not found", i already tried with an `?:` and it didnt work
                field("License", getRepo.license.name, true)
                field("Stars", getRepo.stargazersCount.toString(), true)
                field("Default Branch", getRepo.defaultBranch)
                thumbnail = getRepo.owner.avatarUrl
            }).queue()
    }
}