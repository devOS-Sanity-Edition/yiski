package one.devos.yiski.modules.system

import dev.lizainslie.moeka.core.modules.AbstractModule
import dev.lizainslie.moeka.core.platforms.PlatformId
import dev.lizainslie.moeka.core.platforms.SupportPlatforms
import dev.lizainslie.moeka.platforms.discord.Discord
import one.devos.yiski.modules.system.commands.AboutCommand

@SupportPlatforms(Discord::class)
object SystemModule : AbstractModule(
    "system",
    description = "Core functionality",
    optional = false,
    commands =
        setOf(
            AboutCommand,
        )
) {
    override fun isEnabledForCommunity(communityId: PlatformId): Boolean = true
}