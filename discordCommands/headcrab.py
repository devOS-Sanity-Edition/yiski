from xml.dom.minidom import Element
import discord, tomli, tomli_w
from discord.ext import commands
from random import randint
from mainDiscord import embedCreator
from loguru import logger


class HeadcrabDiscord(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def headcrab(self, ctx, member: discord.Member):
        
        global userHeadcrab
        failRate = randint(1, 9)

        if member.id == ctx.author.id:
            await ctx.send(embed=embedCreator("Headcrab", "You can't headcrab yourself!", 0xFF0000))
            return

        try:
            with open("headcrabs.toml", "rb") as headcrabTOML:
                headcrab = tomli.load(headcrabTOML)
        except FileNotFoundError:
            logger.debug("headcrabs.toml not found!")
            with open("headcrabs.toml", "wb") as headcrabTOML:
                example = {"Example": {"wins": 69, "losses": 420}}
                tomli_w.dump(example, headcrabTOML)
                logger.debug("headcrabs.toml created!")
            with open("headcrabs.toml", "rb") as headcrabTOML:
                headcrab = tomli.load(headcrabTOML)
        
        try:
            userHeadcrab = headcrab[str(member.id)]
        except:
            with open("headcrabs.toml", "ab") as headcrabsUser:
                newUser = {str(member.id): {"wins": 0, "losses": 0}}
                tomli_w.dump(newUser, headcrabsUser)
                userHeadcrab = newUser[str(member.id)]
                logger.debug(f"New user {member.id} (AKA {member.display_name}) has been added to the headcrabs.toml file.")


        def WinOrLose(WinOrLose):
            with open("headcrabs.toml", "r+b") as headcrabsWOR:
                headcrabs = tomli.load(headcrabsWOR)
                with open("headcrabs.toml", "w+b") as headcrabsDEST:
                    for line in headcrabsWOR:
                        element = tomli.loads(line.strip())
                        print(element)
                        if str(member.id) in element:
                            del element[str(member.id)]
                    if WinOrLose == True:
                        new = {str(member.id): {"wins": userHeadcrab["wins"] + 1, "losses": userHeadcrab["losses"]}}

                    elif WinOrLose == False:
                        new = {str(member.id): {"wins": userHeadcrab["wins"], "losses": userHeadcrab["losses"] + 1}}
                    else:
                        embedCreator("Critical Error", "Somehow Studio528#1225 messed this up *really badly* \nPlease contact them", 0x00FF00)
                    headcrabs.update(new)
                    headcrabsDEST.seek(0)
                    tomli_w.dump(headcrabs, headcrabsDEST)

        if failRate == 1:
            fail = userHeadcrab["losses"]
            embed = embedCreator("", f"{ctx.message.author.mention} tried to Headcrab {member.mention}, but failed. (Headcrab Fail #{fail + 1})", 0x8dd594).set_image(url="https://cdn.discordapp.com/attachments/834263755939512351/902748131429580892/headcrab_fail.gif")
            WinOrLose(False)
        else:
            win = userHeadcrab["wins"]
            embed = embedCreator("", f"{ctx.message.author.mention} threw a Headcrab {member.mention}. (Headcrab Win #{win + 1})", 0x8dd594).set_image(url="https://cdn.discordapp.com/attachments/834263755939512351/902748120100773959/headcrab_success.gif")
            WinOrLose(True)

        await ctx.send(embed=embed)


def setup(client):
    client.add_cog(HeadcrabDiscord(client))