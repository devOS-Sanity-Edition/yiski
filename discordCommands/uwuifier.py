import uwuify
from discord.ext import commands
from mainDiscord import embedCreator
from loguru import logger

class UWUifierDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def uwu(self, ctx, *, uwumessage = None):
        if not uwumessage:
            await ctx.send(embed=embedCreator("uwuifier", "pwovide some fwucking text You Fucking Dingus.", 0x00ff00))
        else:
            hell = uwuify.uwu(f"{uwumessage}")
            await ctx.send(embed=embedCreator("uwuifier", f"{hell}", 0x00ff00))


def setup(client):
    client.add_cog(UWUifierDiscord(client))
    logger.debug("Uwuifier Cog loaded.")
