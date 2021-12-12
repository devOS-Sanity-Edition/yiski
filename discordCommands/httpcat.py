import requests
from discord.ext import commands
from mainDiscord import embedCreator


class FunDiscord(commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, client):
        self.client = client

    @commands.command()  # says its a command
    async def httpcat(self, ctx, httpcode: str):  # self always needs to be before ctx in cogs
        httpcodeResponse = requests.get(f"https://http.cat/{httpcode}")
        if httpcodeResponse.status_code != 200:
            embed=embedCreator("Cat not found", "Here is missing cat", 0x404404)
            embed.set_image(url=f"https://http.cat/404.jpg")
            await ctx.reply(embed=embed)
        else:
            embed=embedCreator("Cat", "Here is cat", 0x00a86b)
            embed.set_image(url=f"https://http.cat/{httpcode}.jpg")
            await ctx.reply(embed=embed)


def setup(client):  # actually register the command
    client.add_cog(FunDiscord(client))  # add the cog, you need to use the same thing in the cog as the class above
