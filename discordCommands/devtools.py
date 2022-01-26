# made this command because discord decided to take out devtools, and people in the server still use it so... hi?

from discord.ext import commands

from mainDiscord import embedCreator

class FunDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def devtools(self, ctx):
        embed=embedCreator("So... Discord took out inspect element/devtools. How do I re-enable it?", "", 0x00ff00)
        embed.add_field(name="For Windows:", value=f"Go to `%appdata%\discord` and open up your `settings.json` file. Add ```\"DANGEROUS_ENABLE_DEVTOOLS_ONLY_ENABLE_IF_YOU_KNOW_WHAT_YOURE_DOING\": true,``` to the top of your JSON, and then save it. **Fully** restart Discord [NOT JUST RELOADING IT. **RESTART**]. If You hit CTRL + SHIFT + I, and it works, well... it works.", inline=False)
        embed.add_field(name="For Linux:", value=f"Go to `~/.config/discord/` and open up your `settings.json` file. Add ```\"DANGEROUS_ENABLE_DEVTOOLS_ONLY_ENABLE_IF_YOU_KNOW_WHAT_YOURE_DOING\": true,``` to the top of your JSON, and then save it. **Fully** restart Discord [NOT JUST RELOADING IT. **RESTART**]. If You hit CTRL + SHIFT + I, and it works, well... it works.", inline=False)
        embed.add_field(name="For macOS:", value="beats me lol, tell me where it's located and i'll add it -devin", inline=False)
        embed.add_field(name="End Result:", value="ignore everything else, only the `DANGEROUS_ENABLE_DEVTOOLS_ONLY_ENABLE_IF_YOU_KNOW_WHAT_YOURE_DOING` part is important.", inline=False)
        embed.add_field(name="Original source: ", value=f"[Hi. Click here.](https://www.reddit.com/r/discordapp/comments/sc61n3/comment/hu4fw5x/?utm_source=share&utm_medium=web2x&context=3)")
        embed.set_image(url=f"https://cdn.discordapp.com/attachments/724142050429108245/935768625623756890/unknown.png")
        embed.set_footer(text=f"This is NOT against Discord TOS, also since the original answer was by a Discord Staff member themselves.")
        await ctx.send(embed=embed)


def setup(client):
    client.add_cog(FunDiscord(client))
