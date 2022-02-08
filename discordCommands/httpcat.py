import requests
from discord.ext import commands
from mainDiscord import embedCreator
from loguru import logger


class HTTPCatDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def httpcat(self, ctx, httpcode: str):
        httpcodeResponse = requests.get(f"https://http.cat/{httpcode}")
        if httpcodeResponse.status_code != 200:
            embed=embedCreator("Cat not found", "Here is missing cat", 0x404404)
            embed.set_image(url=f"https://http.cat/404.jpg")
            await ctx.reply(embed=embed)
        else:
            embed=embedCreator("Cat", "Here is cat", 0x00a86b)
            embed.set_image(url=f"https://http.cat/{httpcode}.jpg")
            await ctx.reply(embed=embed)


def setup(client):
    client.add_cog(HTTPCatDiscord(client))
    logger.debug("HTTP Cats Cog loaded.")
