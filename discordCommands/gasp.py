import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import yiskiConf

class GaspDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def gasp(self, ctx):
        await ctx.send(file=discord.File(yiskiConf["videos"]["gasp"]))


def setup(client):
    client.add_cog(GaspDiscord(client))
    logger.debug("Gasp Cog loaded.")
