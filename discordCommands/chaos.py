import inspirobot
from discord.ext import commands
from loguru import logger
from mainDiscord import embedCreator


class ChaosDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def chaos(self, ctx):
        quote = inspirobot.generate()
        embed = embedCreator("Here's your slice of chaos.", "", 0x00ff00)
        embed.set_image(url=f"{quote.url}")
        await ctx.reply(embed=embed)


def setup(client):
    client.add_cog(ChaosDiscord(client))
    logger.debug("InspiroBot [Chaos] Cog loaded.")
