import defectio, uwuify
from defectio import ext
from defectio.ext import commands
from loguru import logger


class UwuifierRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def uwu(self, ctx, *, uwumessage = None):
        if not uwumessage:
            await ctx.reply("# uwuifier\n" + "pwovide some fwucking text You Fucking Dingus.", mention=True)
        else:
            hell = uwuify.uwu(f"{uwumessage}")
            await ctx.reply(f"{hell}")


def setup(bot):
    bot.add_cog(UwuifierRevolt(bot))
    logger.debug("Uwuifier Cog loaded.")
