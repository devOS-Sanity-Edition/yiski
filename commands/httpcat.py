import requests
from discord.ext import commands


class Fun(commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, client):
        self.client = client

    @commands.command()  # says its a command
    async def httpcat(self, ctx, httpcode: str):  # self always needs to be before ctx in cogs
        httpcodeResponse = requests.get(f"https://http.cat/{httpcode}")
        if httpcodeResponse.status_code != 200:
            await ctx.reply(f"[cat](https://http.cat/{httpcodeResponse.status_code}.jpg)", mention_author=True)
        else:
            await ctx.reply(f"[cat](https://http.cat/{httpcode}.jpg)", mention_author=True)


def setup(client):  # actually register the command
    client.add_cog(Fun(client))  # add the cog, you need to use the same thing in the cog as the class above
