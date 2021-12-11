import json
import os

# Maintained Discord.py Lib
import discord
from discord.ext import commands

import requests

with open("config.json", "r") as yiskiConfig:
    yiskiConfiguration = json.load(yiskiConfig)

yiskiActivity = discord.Activity(type=discord.ActivityType.watching, name="snakes slither")

commandPrefix = yiskiConfiguration["yiskiBotPrefix"]

yiskiDiscord = commands.Bot(command_prefix=(commandPrefix), help_command=None, activity=yiskiActivity, status=discord.Status.dnd)

# load cogs on startup
for filename in sorted(os.listdir('./commands/')):
    if filename.endswith('.py'):
        yiskiDiscord.load_extension(f'commands.{filename[:-3]}')

def embedCreator(title, desc, color):
    embed = discord.Embed(
        title=f"{title}",
        description=f"{desc}",
        color=color
    )
    return embed

@yiskiDiscord.event
async def on_ready():
    print("howdy from Discord")


@yiskiDiscord.event
async def on_command_error(ctx: commands.Context, error):
    if isinstance(error, commands.MissingRequiredArgument):
        await ctx.reply(
            f"Hm... looks like this isn't valid syntax? Are you sure you followed the proper syntax for this command? If you need help, refer to `{commandPrefix}help`. If you think this is an error, ping Devin!",
            mention_author=True)


#Reloads all commands
@yiskiDiscord.command(aliases=["relaod"]) #this is here seriously just because i was tired of speed type misspelling it
async def reload(ctx):
    try:
        for filename in os.listdir('./commands/'):
            if filename.endswith('.py'):
                yiskiDiscord.unload_extension(f'commands.{filename[:-3]}')
                yiskiDiscord.load_extension(f'commands.{filename[:-3]}')
        await ctx.send(embed=embedCreator("Reloaded", "All cogs reloaded", 0x00ad10))
    except Exception as e:
        await ctx.send(embed=embedCreator("Error Reloading", f"`{e}`", 0xbf1300))

@yiskiDiscord.command()
async def hello(ctx: commands.Context):
    await ctx.send("howdy nerd", mention_author=True)


@yiskiDiscord.command()
async def httpcat(ctx: commands.Context, httpcode: str):
    httpcodeResponse = requests.get(f"https://http.cat/{httpcode}")
    if httpcodeResponse.status_code != 200:
        await ctx.reply(f"[cat](https://http.cat/{httpcodeResponse.status_code}.jpg)", mention_author=True)
    else:
        await ctx.reply(f"[cat](https://http.cat/{httpcode}.jpg)", mention_author=True)


@yiskiDiscord.command()
async def ghr(ctx: commands.Context, name: str, repo: str):
    await ctx.reply(
        "[Here you go. If nothing shows up, then it's an invalid repo.](https://github.com/" + name + "/" + repo + ")",
        mention_author=True)

@yiskiDiscord.command()
async def memoryleak(ctx: commands.Context):
    await ctx.reply("https://cdn.discordapp.com/attachments/631654541942849536/890731131698282507/video0.mp4", mention_author=True)


yiskiDiscord.run(yiskiConfiguration["yiskiDiscordBotToken"])
