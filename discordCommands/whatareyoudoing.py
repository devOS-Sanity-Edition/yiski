from discord.ext import commands
from loguru import logger
from mainDiscord import embedCreator

class WhatAreYouDoingDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def whatareyoudoing(self, ctx):
        await ctx.send("https://youtu.be/w0szBUI-PaU")

def setup(client):
    client.add_cog(WhatAreYouDoingDiscord(client))
    logger.debug("What Are You Doing Cog loaded.")
