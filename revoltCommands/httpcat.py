import defectio
import requests
from defectio import ext
from defectio.ext import commands


class HTTPCatRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def httpcat(self, ctx, httpcode: str):
        httpcodeResponse = requests.get(f"https://http.cat/{httpcode}")
        if httpcodeResponse.status_code != 200:
            await ctx.reply(f"[cat](https://http.cat/{httpcodeResponse.status_code}.jpg)", mention=True)
        else:
            await ctx.reply(f"[cat](https://http.cat/{httpcode}.jpg)", mention=True)


def setup(bot):
    bot.add_cog(HTTPCatRevolt(bot))
