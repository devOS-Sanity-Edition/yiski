import owo
from discord.ext import commands
from mainDiscord import embedCreator


class FunDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def owo(self, ctx, *, owomessage = None):
        if not owomessage:
            await ctx.send(embed=embedCreator("owoifier", "0w0 pwovide some fucking text You Fucking Dingus ( ˘ ³˘)♥", 0x00ff00))
        else:
            hell = owo.owo(f"{owomessage}")
            await ctx.send(embed=embedCreator("owoifier", f"{hell}", 0x00ff00))


def setup(client):
    client.add_cog(FunDiscord(client))
