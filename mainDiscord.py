import json

# Maintained Discord.py Lib
import discord
from discord.ext import commands

import requests

with open("config.json", "r") as yiskiConfig:
    yiskiConfiguration = json.load(yiskiConfig)

yiskiActivity = discord.Activity(type=discord.ActivityType.watching, name="snakes slither")

commandPrefix = yiskiConfiguration["yiskiBotPrefix"]

yiskiDiscord = commands.Bot(command_prefix=(commandPrefix), help_command=None, activity=yiskiActivity, status=discord.Status.dnd)


@yiskiDiscord.event
async def on_ready():
    print("howdy from Discord")


@yiskiDiscord.event
async def on_command_error(ctx: commands.Context, error):
    if isinstance(error, commands.MissingRequiredArgument):
        await ctx.reply(
            f"Hm... looks like this isn't valid syntax? Are you sure you followed the proper syntax for this command? If you need help, refer to `{commandPrefix}help`. If you think this is an error, ping Devin!",
            mention_author=True)


@yiskiDiscord.command()
async def help(ctx):
    yiskiHelpEmbed = discord.Embed(title="Yiski Help",
                                   description="Here's the commands you can use on Yiski. Note that the Discord version won't be well maintained, as Revolt is the primary goal for this bot.",
                                   color=0x00a86b)
    yiskiHelpEmbed.add_field(name="help", value=f"- Help Command\n- `{commandPrefix}help`")
    yiskiHelpEmbed.add_field(name="hello", value=f"- Hello Command\n- `{commandPrefix}hello`")
    yiskiHelpEmbed.add_field(name="httpcat", value=f"- HTTP Cats go brrr\n- `{commandPrefix}httpcat [http code]`")
    yiskiHelpEmbed.add_field(name="ghr", value=f"- Preview a GitHub Repo\n- `{commandPrefix}ghr [username/orgname] [reponame]`")
    yiskiHelpEmbed.add_field(name="memoryleak", value=f"- Funni Memory Leak video go brrr\n- `{commandPrefix}memoryleak`")
    yiskiHelpEmbed.set_footer(text="Bot writen by HiItsDevin_, powered by Py-cord, a Discord.py continuation of the original Discord.py library. Written with 💖!")


    await ctx.reply(embed=yiskiHelpEmbed)


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
