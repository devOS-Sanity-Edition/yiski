import defectio, owo
from defectio import ext
from defectio.ext import commands
from loguru import logger


class OwoifierRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def owo(self, ctx, *, owomessage=None):
        if not owomessage:
            await ctx.reply("# owoifier\n" + "0w0 pwovide some fucking text You Fucking Dingus ( ˘ ³˘)♥", mention=True)
        else:
            hell = owo.owo(f"{owomessage}")
            await ctx.reply(f"{hell}")


def setup(bot):
    bot.add_cog(OwoifierRevolt(bot))
    logger.debug("Owoifier Cog loaded.")
