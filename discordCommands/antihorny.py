import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import yiskiConf

class AntiHornyDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def antihorny(self, ctx):
        await ctx.send("go touch some grass lmao \n", file=discord.File(yiskiConf["videos"]["antihorny"]))


def setup(client):
    client.add_cog(AntiHornyDiscord(client))
    logger.debug("Anti Horny Cog loaded.")
