import defectio
from defectio import ext
from defectio.ext import commands
from loguru import logger


class TokenRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def token(self, ctx):
        await ctx.reply("[be careful with your token in config.toml lol](https://cdn.discordapp.com/attachments/724142050429108245/919572878951862323/unknown.png)", mention=True)


def setup(bot):
    bot.add_cog(TokenRevolt(bot))
    logger.debug("Token Cog loaded.")
