import defectio
from defectio import ext
from defectio.ext import commands
from loguru import logger


class HelloRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def hello(self, ctx):
        await ctx.reply("howdy nerd", mention=True)


def setup(bot):
    bot.add_cog(HelloRevolt(bot))
    logger.debug("Hello Cog loaded.")
