from zalgo_text import zalgo
from discord.ext import commands
from mainDiscord import embedCreator
from loguru import logger

class ZalgoifierDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def zalgo(self, ctx, *, zalgomessage = None):
        if not zalgomessage:
            await ctx.send(embed=embedCreator("zalgoifier", zalgo.zalgo().zalgofy(f"provide some fucking text You Fucking Dingus."), 0x00ff00))
        else:
            hell = zalgo.zalgo().zalgofy(f"{zalgomessage}")
            await ctx.send(embed=embedCreator("zalgoifier", f"{hell}", 0x00ff00))


def setup(client):
    client.add_cog(ZalgoifierDiscord(client))
    logger.debug("Zalgoifier Cog loaded.")
