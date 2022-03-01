import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import embedCreator, yiskiConf

class WhatAreYouDoingDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def whatareyoudoing(self, ctx):
        await ctx.send(file=discord.File(yiskiConf["videos"]["whatareyoudoing"]))

def setup(client):
    client.add_cog(WhatAreYouDoingDiscord(client))
    logger.debug("What Are You Doing Cog loaded.")
