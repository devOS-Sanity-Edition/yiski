package one.devos.yiski.modules.`fun`

import dev.lizainslie.moeka.core.modules.AbstractModule
import dev.lizainslie.moeka.core.platforms.PlatformId
import dev.lizainslie.moeka.core.platforms.SupportPlatforms
import dev.lizainslie.moeka.platforms.discord.Discord
import one.devos.yiski.modules.`fun`.commands.SongCommand

@SupportPlatforms(Discord::class)
object FunModule : AbstractModule(
    "fun",
    description = "Like a clown but not scary",
    optional = true,
    commands =
        setOf(
            SongCommand,
        )
) {
    override fun isEnabledForCommunity(communityId: PlatformId): Boolean = true
}