import discord
from discord.ext import commands
from mainDiscord import commandPrefix


class UtilDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def help(self, ctx):
        yiskiHelpEmbed = discord.Embed(title="Yiski Help",
                                       description="Here's the commands you can use on Yiski. Note that the Discord version won't be well maintained, as Revolt is the primary goal for this bot.",
                                       color=0x00a86b)
        yiskiHelpEmbed.add_field(name="hello", value=f"- Hello Command\n- `{commandPrefix}hello`")
        yiskiHelpEmbed.add_field(name="httpcat", value=f"- HTTP Cats go brrr\n- `{commandPrefix}httpcat [http code]`")
        yiskiHelpEmbed.add_field(name="ghr",
                                 value=f"- Preview a GitHub Repo\n- `{commandPrefix}ghr [username/orgname] [reponame]`")
        yiskiHelpEmbed.add_field(name="memoryleak",
                                 value=f"- Funni Memory Leak video go brrr\n- `{commandPrefix}memoryleak`")
        yiskiHelpEmbed.add_field(name="gasp",
                                 value=f"- Just find out for yourself.\n- `{commandPrefix}gasp`")
        yiskiHelpEmbed.add_field(name="token",
                                 value=f"- So about that funky config.json5...\n- `{commandPrefix}token`")
        yiskiHelpEmbed.set_footer(
            text="Bot writen by HiItsDevin_, powered by Py-cord, a Discord.py continuation of the original Discord.py library. Written with 💖!")

        await ctx.reply(embed=yiskiHelpEmbed)


def setup(client):
    client.add_cog(UtilDiscord(client))
