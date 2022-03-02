import defectio
from zalgo_text import zalgo
from defectio import ext
from defectio.ext import commands
from loguru import logger


class ZalgoifierRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def zalgo(self, ctx, *, zalgomessage = None):
        if not zalgomessage:
            await ctx.reply("# zalgoifier\n" + zalgo.zalgo().zalgofy(f"provide some fucking text You Fucking Dingus."), mention=True)
        else:
            hell = zalgo.zalgo().zalgofy(f"{zalgomessage}")
            await ctx.reply(f"{hell}")


def setup(bot):
    bot.add_cog(ZalgoifierRevolt(bot))
    logger.debug("Zalgoifier Cog loaded.")
