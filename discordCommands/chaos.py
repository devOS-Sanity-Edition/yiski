import inspirobot
from discord.ext import commands

from mainDiscord import embedCreator


class FunDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def chaos(self, ctx):
        quote = inspirobot.generate()
        embed = embedCreator("Here's your slice of chaos.", "", 0x00ff00)
        embed.set_image(url=f"{quote.url}")
        await ctx.reply(embed=embed)


def setup(client):
    client.add_cog(FunDiscord(client))
