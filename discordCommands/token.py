from discord.ext import commands

from mainDiscord import embedCreator


class FunDiscord(commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, client):
        self.client = client

    @commands.command()  # says its a command
    async def token(self, ctx):  # self always needs to be before ctx in cogs
        embed=embedCreator("be careful with your token in config.json5 lol", "", 0x00ff00)
        embed.set_image(url=f"https://cdn.discordapp.com/attachments/724142050429108245/919572878951862323/unknown.png")
        await ctx.send(embed=embed)


def setup(client):  # actually register the command
    client.add_cog(FunDiscord(client))  # add the cog, you need to use the same thing in the cog as the class above
