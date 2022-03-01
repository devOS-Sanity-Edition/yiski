import defectio
from defectio import ext
from defectio.ext import commands
from loguru import logger
from mainRevolt import yiskiConf

class RTXRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def rtx(self, ctx):
        await ctx.reply(file=defectio.File(yiskiConf["videos"]["rtx"]))


def setup(bot):
    bot.add_cog(RTXRevolt(bot))
    logger.debug("RTX Cog loaded.")
