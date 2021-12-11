import discord
from discord.ext import commands
from mainDiscord import commandPrefix, yiskiDiscord


class Util(commands.Cog): #this defines what "class" things will be in, can be completely custom
    def __init__(self, client): #needed or smth
        super().__init__(help_command=None)
        self.client = client

    @yiskiDiscord.command() #says its a command
    async def help(self, ctx): #self always needs to be before ctx in cogs
        yiskiHelpEmbed = discord.Embed(title="Yiski Help",
                                       description="Here's the commands you can use on Yiski. Note that the Discord version won't be well maintained, as Revolt is the primary goal for this bot.",
                                       color=0x00a86b)
        yiskiHelpEmbed.add_field(name="help", value=f"- Help Command\n- `{commandPrefix}help`")
        yiskiHelpEmbed.add_field(name="hello", value=f"- Hello Command\n- `{commandPrefix}hello`")
        yiskiHelpEmbed.add_field(name="httpcat", value=f"- HTTP Cats go brrr\n- `{commandPrefix}httpcat [http code]`")
        yiskiHelpEmbed.add_field(name="ghr",
                                 value=f"- Preview a GitHub Repo\n- `{commandPrefix}ghr [username/orgname] [reponame]`")
        yiskiHelpEmbed.add_field(name="memoryleak",
                                 value=f"- Funni Memory Leak video go brrr\n- `{commandPrefix}memoryleak`")
        yiskiHelpEmbed.set_footer(
            text="Bot writen by HiItsDevin_, powered by Py-cord, a Discord.py continuation of the original Discord.py library. Written with 💖!")

        await ctx.reply(embed=yiskiHelpEmbed)

def setup(client): #actually register the command
    client.add_cog(Util(client)) #add the cog, you need to use the same thing in the cog as the class above