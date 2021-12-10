import json

import defectio
from defectio.ext import commands

with open("token.json", "r") as yiskiBotTokenLoader:
    yiskiBotToken = json.load(yiskiBotTokenLoader)


yiski = commands.Bot(command_prefix="/", help_command=None)

@yiski.event
async def on_ready():
    print("howdy")

@yiski.event
async def on_command_error(ctx,error):
    if isinstance(error, commands.MissingRequiredArgument):
        await ctx.reply("Hm... looks like this isn't valid syntax? Are you sure you followed the proper syntax for this command? If you need help, refer to `/help`. If you think this is an error, ping Devin!", mention = True)

@yiski.command()
async def help(ctx):
    await ctx.reply("| Command Name | Description           | Command                           |\n" +
                    "|--------------| ----------------------|-----------------------------------|\n" +
                    "| **hello**    | Hello Command         | /hello                            |\n" +
                    "| **httpcat**  | HTTP Cats go brrr     | /httpcat [http code]              |\n" +
                    "| **ghr**      | Preview a Github Repo | /ghr [username/orgname] [reponame]|\n\n" +
                    "Bot writen by **HiItsDevin_**, powered by **[defectio](https://github.com/Darkflame72/defectio)**, a Revolt Python bot library. Written with 💖!", mention = True)

@yiski.command()
async def hello(ctx):
    await ctx.reply("howdy nerd", mention = True)

@yiski.command()
async def httpcat(ctx, httpcode: str):
    await ctx.reply("[cat](https://http.cat/" + httpcode + ")", mention = True)

@yiski.command()
async def ghr(ctx, name: str, repo: str):
    await ctx.reply("[Here you go. If nothing shows up, then it's an invalid repo.](https://github.com/" + name + "/" + repo + ")", mention = True)

yiski.run(yiskiBotToken["yiskiBotToken"])