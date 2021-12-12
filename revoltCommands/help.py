import defectio
from defectio import ext
from defectio.ext import commands
from mainRevolt import commandPrefix


class HelpRevolt(commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def help(self, ctx):
        await ctx.reply(
            "| Command Name  | Description           | Command                                         |\n" +
            "|---------------| ----------------------|-------------------------------------------------|\n" +
            f"| **hello**    | Hello Command         | {commandPrefix}hello                            |\n" +
            f"| **httpcat**  | HTTP Cats go brrr     | {commandPrefix}httpcat [http code]              |\n" +
            f"| **ghr**      | Preview a Github Repo | {commandPrefix}ghr [username/orgname] [reponame]|\n\n" +
            "Bot writen by **HiItsDevin_**, powered by **[defectio](https://github.com/Darkflame72/defectio)**, a Revolt Python bot library. Written with 💖!",
            mention=True)


def setup(bot):  # actually register the command
    bot.add_cog(HelpRevolt(bot))  # add the cog, you need to use the same thing in the cog as the class above
