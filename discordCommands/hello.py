from discord.ext import commands
from loguru import logger
from mainDiscord import embedCreator


class HelloDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def hello(self, ctx):
        await ctx.send(embed=embedCreator("Hello World!", f"& Howdy Nerd, aka {ctx.author.mention}", 0x00ff00))


def setup(client):
    client.add_cog(HelloDiscord(client))
    logger.debug("Hello Cog loaded.")
