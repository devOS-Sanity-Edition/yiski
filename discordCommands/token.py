from discord.ext import commands
from loguru import logger
from mainDiscord import embedCreator

class TokenDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def token(self, ctx):
        embed=embedCreator("be careful with your token in config.toml lol", "", 0x00ff00)
        embed.set_image(url=f"https://cdn.discordapp.com/attachments/724142050429108245/919572878951862323/unknown.png")
        await ctx.send(embed=embed)


def setup(client):
    client.add_cog(TokenDiscord(client))
    logger.debug("Token Cog loaded.")
