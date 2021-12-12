from discord.ext import commands


class Fun(commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, client):
        self.client = client

    @commands.command()  # says its a command
    async def memoryleak(self, ctx):  # self always needs to be before ctx in cogs
        await ctx.send("https://youtu.be/QbFtogkOFLc")


def setup(client):  # actually register the command
    client.add_cog(Fun(client))  # add the cog, you need to use the same thing in the cog as the class above