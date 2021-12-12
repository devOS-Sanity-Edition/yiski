import defectio.ext.commands
import requests
from defectio.ext import commands


class HTTPCatRevolt(defectio.ext.commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, bot):
        self.bot = bot

    @commands.command()  # says its a command
    async def httpcat(self, ctx, httpcode: str):
        httpcodeResponse = requests.get(f"https://http.cat/{httpcode}")
        if httpcodeResponse.status_code != 200:
            await ctx.reply(f"[cat](https://http.cat/{httpcodeResponse.status_code}.jpg)", mention=True)
        else:
            await ctx.reply(f"[cat](https://http.cat/{httpcode}.jpg)", mention=True)


def setup(bot):  # actually register the command
    bot.add_cog(HTTPCatRevolt(bot))  # add the cog, you need to use the same thing in the cog as the class above
