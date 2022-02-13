import discord
from discord.ext import commands
from loguru import logger

class EmoteDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def emote(self, ctx, emoji: discord.Emoji):
        await ctx.send(emoji.url)

def setup(client):
    client.add_cog(EmoteDiscord(client))
    logger.debug("Emote Cog loaded.")