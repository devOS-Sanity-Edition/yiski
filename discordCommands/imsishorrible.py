from discord.ext import commands
from loguru import logger


class IMSIsHorribleDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def ims(self, ctx):
        await ctx.reply("<@366702774403989504> you're fucking horrible\n https://cdn.discordapp.com/attachments/670086223322284049/921252194395758622/unknown.png",
                        mention_author=True)


def setup(client):
    client.add_cog(IMSIsHorribleDiscord(client))
    logger.debug("IMS Is Horrible Cog loaded.")
