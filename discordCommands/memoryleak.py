from discord.ext import commands
from loguru import logger

class MemoryLeakDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def memoryleak(self, ctx):
        await ctx.send("https://youtu.be/QbFtogkOFLc")


def setup(client):
    client.add_cog(MemoryLeakDiscord(client))
    logger.debug("Memory Leak Cog loaded.")