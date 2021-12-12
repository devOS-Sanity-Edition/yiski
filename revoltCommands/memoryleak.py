import defectio
from defectio import ext
from defectio.ext import commands


class MemoryLeakRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def memoryleak(self, ctx):
        await ctx.reply("[be fucking careful](https://youtu.be/QbFtogkOFLc)", mention=True)


def setup(bot):
    bot.add_cog(MemoryLeakRevolt(bot))
