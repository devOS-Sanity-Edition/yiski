from discord.ext import commands


class Fun(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def memoryleak(self, ctx):
        await ctx.send("https://youtu.be/QbFtogkOFLc")


def setup(client):
    client.add_cog(Fun(client))