import json
# Revolt Python Lib
import defectio
from defectio.ext import commands

import requests

with open("config.json", "r") as yiskiConfig:
    yiskiConfiguration = json.load(yiskiConfig)

commandPrefix = yiskiConfiguration["yiskiBotPrefix"]

yiskiRevolt = defectio.ext.commands.Bot(command_prefix=(commandPrefix), help_command=None)


@yiskiRevolt.event
async def on_ready():
    print("howdy from Revolt")


@yiskiRevolt.event
async def on_command_error(ctx, error):
    if isinstance(error, commands.MissingRequiredArgument):
        await ctx.reply(
            f"Hm... looks like this isn't valid syntax? Are you sure you followed the proper syntax for this command? If you need help, refer to `{commandPrefix}help`. If you think this is an error, ping Devin!",
            mention=True)


@yiskiRevolt.command()
async def help(ctx):
    await ctx.reply("| Command Name  | Description           | Command                                         |\n" +
                    "|---------------| ----------------------|-------------------------------------------------|\n" +
                    f"| **hello**    | Hello Command         | {commandPrefix}hello                            |\n" +
                    f"| **httpcat**  | HTTP Cats go brrr     | {commandPrefix}httpcat [http code]              |\n" +
                    f"| **ghr**      | Preview a Github Repo | {commandPrefix}ghr [username/orgname] [reponame]|\n\n" +
                    "Bot writen by **HiItsDevin_**, powered by **[defectio](https://github.com/Darkflame72/defectio)**, a Revolt Python bot library. Written with 💖!",
                    mention=True)


@yiskiRevolt.command()
async def hello(ctx):
    await ctx.reply("howdy nerd", mention=True)


@yiskiRevolt.command()
async def httpcat(ctx, httpcode: str):
    httpcodeResponse = requests.get(f"https://http.cat/{httpcode}")
    if httpcodeResponse.status_code != 200:
        await ctx.reply(f"[cat](https://http.cat/{httpcodeResponse.status_code}.jpg)", mention=True)
    else:
        await ctx.reply(f"[cat](https://http.cat/{httpcode}.jpg)", mention=True)


@yiskiRevolt.command()
async def ghr(ctx, name: str, repo: str):
    await ctx.reply(
        "[Here you go. If nothing shows up, then it's an invalid repo.](https://github.com/" + name + "/" + repo + ")",
        mention=True)


yiskiRevolt.run(yiskiConfiguration["yiskiRevoltBotToken"])
