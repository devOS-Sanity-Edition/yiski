from discord import channel
from discord.ext import commands
from mainDiscord import embedCreator, ventChannel
import discord, json5, asyncio, datetime

wipeTime = datetime.time(hour=8)

class WindshieldWiper(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def clearvent(self, ctx):
        await ctx.send("FUCK, NOOOOOOOO")
        await self.timedWipe(datetime.datetime.utcnow())


    async def wipeLogic(self):
        while True:
            now = datetime.datetime.utcnow()
            if now.time() < wipeTime:
                date = now.date()
            else:
                date = now.date() + datetime.timedelta(days=1)
            then = datetime.datetime.combine(date, wipeTime)
            await discord.utils.sleep_until(then)
            await asyncio.sleep(5)
            await self.timedWipe(now)

    async def timedWipe(self, now):
        archiveChannel = self.client.get_channel(834263755939512351)
        ventChannelReal = self.client.get_channel(920541339970576425)

        infoDict = {"messages": []}
        messageCount = 0

        async with archiveChannel.typing():
            async for message in ventChannelReal.history(limit=None, oldest_first=True):
                messageDict = {
                    str(message.id): {
                        "content": message.content,
                        "author-id": message.author.id
                    }
                }

                infoDict["messages"].append(messageDict)
                messageCount += 1

        with open("fucking hell.json5", "w") as aaaaaaa:
            messageJson = json5.dump(infoDict, aaaaaaa, indent=2)

        def pincheck(m):
            return not m.pinned

        await ventChannelReal.purge(limit=messageCount, bulk=True, check=pincheck, oldest_first=True)

        h = discord.File(fp="fucking hell.json5", filename="hhhh.json5")
        await archiveChannel.send(file=h)


        #def __enter__(self):
        #    return self


def setup(client):
    client.add_cog(WindshieldWiper(client))
    #client.loop.create_task(client.get_cog("Windshieldwiper").wipeLogic())
