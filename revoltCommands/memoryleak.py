import defectio
from defectio import ext
from defectio.ext import commands
from loguru import logger
from mainRevolt import yiskiConf

class MemoryLeakRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def memoryleak(self, ctx):
        await ctx.reply(file=defectio.File(yiskiConf["videos"]["memoryleak"]))


def setup(bot):
    bot.add_cog(MemoryLeakRevolt(bot))
    logger.debug("Memory Leak Cog loaded.")
