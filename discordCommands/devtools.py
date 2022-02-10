# made this command because discord decided to take out devtools, and people in the server still use it so... hi?
import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import embedCreator, yiskiConf
import tomli

class DevToolsDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def devtools(self, ctx):
        try:
            with open("assets\devtools.toml", "rb") as devtoolsTOML:
                devtools = tomli.load(devtoolsTOML)
        except FileNotFoundError:
            logger.error("wait what the fuck? why is the devtools.toml file missing? contact devin for it, or grab it off of github?")

        devtoolsConfigImage = discord.File(yiskiConf["images"]["static"]["devtools"])

        embed=embedCreator(devtools["embed"]["title"], devtools["embed"]["text"], 0x00ff00)
        embed.add_field(name=devtools["windows"]["title"], value=devtools["windows"][f"text"], inline=False)
        embed.add_field(name=devtools["linux"]["title"], value=devtools["linux"][f"text"], inline=False)
        embed.add_field(name=devtools["macos"]["title"], value=devtools["macos"][f"text"], inline=False)
        embed.add_field(name=devtools["result"]["title"], value=devtools["result"][f"text"], inline=False)
        embed.add_field(name=devtools["source"]["title"], value=devtools["source"][f"text"])
        embed.set_image(url="attachment://devtoolsconfig.png")
        embed.set_footer(text=f"This is NOT against Discord TOS, also since the original answer was by a Discord Staff member themselves.")
        await ctx.send(file=devtoolsConfigImage, embed=embed)


def setup(client):
    client.add_cog(DevToolsDiscord(client))
    logger.debug("Dev Tools Cog loaded.")
