import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import yiskiConf

class RTXDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def rtx(self, ctx):
        await ctx.send(file=discord.File(yiskiConf["videos"]["rtx"]))


def setup(client):
    client.add_cog(RTXDiscord(client))
    logger.debug("RTX Cog loaded.")