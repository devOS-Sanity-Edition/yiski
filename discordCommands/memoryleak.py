import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import yiskiConf

class MemoryLeakDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def memoryleak(self, ctx):
        await ctx.send(file=discord.File(yiskiConf["videos"]["memoryleak"]))


def setup(client):
    client.add_cog(MemoryLeakDiscord(client))
    logger.debug("Memory Leak Cog loaded.")