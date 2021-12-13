import defectio
from defectio import ext
from defectio.ext import commands
from loguru import logger


class GaspRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def gasp(self, ctx):
        await ctx.reply("[😮](https://youtu.be/GwIlt8pJdHg)", mention=True)


def setup(bot):
    bot.add_cog(GaspRevolt(bot))
    logger.debug("Gasp Cog loaded.")
