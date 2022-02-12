import supsubscript
from defectio.ext import commands
from loguru import logger

class subscriptifierRevolt(commands.Cog):
    def __init__(self, bot):
        self.client = bot

    @commands.command()
    async def subscript(self, ctx, *, subscriptmessage = None):
        if not subscriptmessage:
            await ctx.send("# subscriptifier\n" + f"{supsubscript.subscript('provide some fucking text you dingus')}")
        else:
            hell = supsubscript.subscript(f"{subscriptmessage}")
            await ctx.send("# subscriptifier\n" + f"{hell}")

class superscriptifierRevolt(commands.Cog):
    def __init__(self, bot):
        self.client = bot

    @commands.command()
    async def superscript(self, ctx, *, superscriptmessage = None):
        if not superscriptmessage:
            await ctx.send("# superscriptifier\n" + f"{supsubscript.superscript('provide some fucking text you dingus')}")
        else:
            hell = supsubscript.superscript(f"{superscriptmessage}")
            await ctx.send("# superscriptifier\n" + f"{hell}")

def setup(bot):
    bot.add_cog(subscriptifierRevolt(bot))
    bot.add_cog(superscriptifierRevolt(bot))
    logger.debug("Subscriptifier/Superscriptifier Cog loaded.")
