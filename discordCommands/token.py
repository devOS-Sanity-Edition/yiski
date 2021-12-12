from discord.ext import commands

from mainDiscord import embedCreator


class FunDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def token(self, ctx):
        embed=embedCreator("be careful with your token in config.json5 lol", "", 0x00ff00)
        embed.set_image(url=f"https://cdn.discordapp.com/attachments/724142050429108245/919572878951862323/unknown.png")
        await ctx.send(embed=embed)


def setup(client):
    client.add_cog(FunDiscord(client))
