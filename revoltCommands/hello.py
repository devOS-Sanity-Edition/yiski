import defectio.ext.commands
from defectio.ext import commands


class FunRevolt(defectio.ext.commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, bot: commands.Bot):
        self.bot = bot

    @commands.command()  # says its a command
    async def hello(self, ctx):
        await ctx.reply("howdy nerd", mention=True)


def setup(bot):  # actually register the command
    bot.add_cog(FunRevolt(bot))  # add the cog, you need to use the same thing in the cog as the class above
