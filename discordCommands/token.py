import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import embedCreator, yiskiConf

class TokenDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def token(self, ctx):
        tokenJoke = discord.File(yiskiConf["images"]["static"]["token"])
        embed=embedCreator("be careful with your token in config.toml lol", "", 0x00ff00)
        embed.set_image(url="attachment://token.png")
        await ctx.send(file=tokenJoke, embed=embed)


def setup(client):
    client.add_cog(TokenDiscord(client))
    logger.debug("Token Cog loaded.")
