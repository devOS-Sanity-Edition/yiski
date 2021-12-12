import defectio
from defectio import ext
from defectio.ext import commands


class MemoryLeakRevolt(
    defectio.ext.commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, bot):
        self.bot = bot

    @commands.command()  # says its a command
    async def memoryleak(self, ctx):
        await ctx.reply("[be fucking careful](https://youtu.be/QbFtogkOFLc)", mention=True)


def setup(bot):  # actually register the command
    bot.add_cog(MemoryLeakRevolt(bot))  # add the cog, you need to use the same thing in the cog as the class above
