import discord
from discord.ext import commands
from loguru import logger
from mainDiscord import yiskiConf, embedCreator

class IMSIsHorribleDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def ims(self, ctx):

        imsImage = discord.File(yiskiConf["images"]["static"]["ims"])
        embed = embedCreator("IMS is a horrible person", "", 0x00ff00)
        embed.set_image(url="attachment://dreadfulims.png")
        await ctx.send(file=imsImage, embed=embed)

def setup(client):
    client.add_cog(IMSIsHorribleDiscord(client))
    logger.debug("IMS Is Horrible Cog loaded.")
