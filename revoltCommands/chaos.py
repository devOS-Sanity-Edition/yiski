import inspirobot
from defectio.ext import commands
from loguru import logger


class ChaosRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def chaos(self, ctx):
        quote = inspirobot.generate()
        await ctx.reply(f"[Here's your slice of chaos.]({quote.url})", mention=True)


def setup(bot):
    bot.add_cog(ChaosRevolt(bot))
    logger.debug("InspiroBot [Chaos] Cog loaded.")
