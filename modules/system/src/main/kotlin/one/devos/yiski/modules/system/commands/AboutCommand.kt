package one.devos.yiski.modules.system.commands

import dev.kord.common.Color
import dev.kord.common.entity.DiscordPartialEmoji
import dev.kord.common.entity.MessageFlag
import dev.kord.common.entity.SeparatorSpacingSize
import dev.kord.core.behavior.interaction.respondPublic
import dev.kord.rest.builder.component.actionRow
import dev.kord.rest.builder.component.section
import dev.kord.rest.builder.component.separator
import dev.kord.rest.builder.component.textDisplay
import dev.kord.rest.builder.message.container
import dev.kord.rest.builder.message.messageFlags
import dev.lizainslie.moeka.core.commands.defineCommand
import dev.lizainslie.moeka.platforms.discord.Discord
import dev.lizainslie.moeka.platforms.discord.commands.DiscordCommandContext
import dev.lizainslie.moeka.platforms.discord.commands.enforceDiscordSlash
import one.devos.yiski.common.GitHub
import one.devos.yiski.common.Versions

val AboutCommand = defineCommand(
    name = "about",
    description = "About Yiski"
) {
    platform(Discord)

    handle {
        if (this is DiscordCommandContext) {
            val self = Discord.kord.getSelf()
            val selfAvatar = self.avatar?.cdnUrl?.toUrl() ?: Discord.kord.getSelf().defaultAvatar.cdnUrl.toUrl()
            val repository = GitHub.getRepository()

            enforceDiscordSlash {
                interaction.respondPublic {
                    messageFlags {
                        +MessageFlag.IsComponentsV2
                    }

                    container {
                        accentColor = Color(0, 168, 107)

                        section {
                            thumbnailAccessory { url = selfAvatar }
                            textDisplay { content = "# Yiski" }
                            textDisplay { content = "the inhouse bot that wants to be sprung off a cliff" }
                        }

                        separator {
                            divider = true
                            spacing = SeparatorSpacingSize.Small
                        }

                        textDisplay { content = "## About" }

                        textDisplay {
                            content = """
                                Yiski is an in-house Discord bot developed by people in devOS: Sanity Edition, mainly lead by [asojidev](https://github.com/asoji). The aim is to be an all-in-one bot that provides utility, moderation, and silly shenanigans. It also fills in gaps for things that either didn't exist or weren't good enough for our needs, primarily a vent channel wiper and TTS.
                                
                                There have been previous bots before this made by different people in the devOS community, but they have had their issues, and sometimes other solutions don't work out as well as we want them to.
                            """.trimIndent()
                        }

                        separator {
                            divider = true
                            spacing = SeparatorSpacingSize.Small
                        }

                        textDisplay { content = "## Technical details" }

                        textDisplay {
                            content = """
                                ### Major Versions
                                Yiski version: ${Versions.YISKI_VERSION}
                                Kord version: ${Versions.KORD_VERSION}
                                Moeka version: ${Versions.MOEKA_VERSION}
                                Kotlin version: ${KotlinVersion.CURRENT}
                            """.trimIndent()
                        }

                        separator {
                            divider = true
                            spacing = SeparatorSpacingSize.Small
                        }

                        textDisplay { content = "-# this bot was written on less braincells than an orange cat" }

                        actionRow {
                            linkButton(repository.url) {
                                emoji = DiscordPartialEmoji(name = "\uD83D\uDCD8")
                                label = "GitHub"
                            }

                            linkButton("${repository.url}/issues") {
                                emoji = DiscordPartialEmoji(name = "\uD83D\uDD16")
                                label = "Issues / File a bug or feedback"
                            }
                        }
                    }

                }
            }
        }
    }
}