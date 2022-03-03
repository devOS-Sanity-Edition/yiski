import defectio
from defectio import ext
from defectio.ext import commands
from loguru import logger
from mainRevolt import yiskiConf

class AntiHornyRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def antihorny(self, ctx):
        await ctx.reply("go touch some grass lmao \n")
        await ctx.reply(file=defectio.File(yiskiConf["videos"]["antihorny"]))


def setup(bot):
    bot.add_cog(AntiHornyRevolt(bot))
    logger.debug("Anti Horny Cog loaded.")
