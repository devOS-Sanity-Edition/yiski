import supsubscript
from supsubscript import *
from discord.ext import commands
from mainDiscord import embedCreator
from loguru import logger

class subscriptifierDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def subscript(self, ctx, *, subscriptmessage = None):
        if not subscriptmessage:
            await ctx.send(embed=embedCreator("subscriptifier", supsubscript.subscript('provide some fucking text you dingus'), 0x00ff00))
        else:
            hell = supsubscript.subscript(f"{subscriptmessage}")
            await ctx.send(embed=embedCreator("subscriptifier", f"{hell}", 0x00ff00))

class superscriptifierDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def superscript(self, ctx, *, superscriptmessage = None):
        if not superscriptmessage:
            await ctx.send(embed=embedCreator("superscriptifier", supsubscript.superscript('provide some fucking text you dingus'), 0x00ff00))
        else:
            hell = supsubscript.superscript(f"{superscriptmessage}")
            await ctx.send(embed=embedCreator("superscriptifier", f"{hell}", 0x00ff00))

def setup(client):
    client.add_cog(subscriptifierDiscord(client))
    client.add_cog(superscriptifierDiscord(client))
    logger.debug("Subscriptifier/Superscriptifier Cog loaded.")
