from discord.ext import commands

from mainDiscord import embedCreator


class FunDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def hello(self, ctx):
        await ctx.send(embed=embedCreator("Hello World!", f"& Howdy Nerd, aka {ctx.author.mention}", 0x00ff00))


def setup(client):
    client.add_cog(FunDiscord(client))
