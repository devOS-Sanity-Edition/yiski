import defectio
from defectio import ext
from defectio.ext import commands
from loguru import logger
from mainRevolt import yiskiConf

class WhatAreYouDoingRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def whatareyoudoing(self, ctx):
        await ctx.reply(file=defectio.File(yiskiConf["videos"]["whatareyoudoing"]))


def setup(bot):
    bot.add_cog(WhatAreYouDoingRevolt(bot))
    logger.debug("What Are You Doing Cog loaded.")
