import defectio
from defectio import ext
from defectio.ext import commands


class TokenRevolt(commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, bot):
        self.bot = bot

    @commands.command()  # says its a command
    async def token(self, ctx):
        await ctx.reply("[be careful with your token in config.json5 lol](https://cdn.discordapp.com/attachments/724142050429108245/919572878951862323/unknown.png)", mention=True)


def setup(bot):  # actually register the command
    bot.add_cog(TokenRevolt(bot))  # add the cog, you need to use the same thing in the cog as the class above
