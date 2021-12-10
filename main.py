import json
from defectio.ext import commands

with open("token.json", "r") as yiskiBotTokenLoader:
    yiskiBotToken = json.load(yiskiBotTokenLoader)


yiski = commands.Bot(command_prefix="/")

@yiski.event
async def on_ready():
    print("howdy")

@yiski.command()
async def hello(ctx):
    await ctx.reply("howdy", mention = True)

@yiski.command()
async def httpcat(ctx, httpcode: str):
    await ctx.reply("[cat](https://http.cat/" + httpcode + ")")

yiski.run(yiskiBotToken["yiskiBotToken"])