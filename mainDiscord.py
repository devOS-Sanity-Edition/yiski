import json
import os

# Maintained Discord.py Lib
import discord
from discord.ext import commands

with open("config.json", "r") as yiskiConfig:
    yiskiConfiguration = json.load(yiskiConfig)

yiskiActivity = discord.Activity(type=discord.ActivityType.watching, name="snakes slither")

commandPrefix = yiskiConfiguration["yiskiBotPrefix"]

intents = discord.Intents().all()

yiskiDiscord = commands.Bot(help_command=None, command_prefix=commandPrefix, intents=intents, activity=yiskiActivity,
                            status=discord.Status.dnd)

# load cogs on startup
for filename in sorted(os.listdir('./discordCommands/')):
    if filename.endswith('.py'):
        yiskiDiscord.load_extension(f'discordCommands.{filename[:-3]}')


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


# Reloads all commands
@yiskiDiscord.command(
    aliases=["relaod"])  # this is here seriously just because i was tired of speed type misspelling it
async def reload(ctx):
    try:
        for filename in os.listdir('./discordCommands/'):
            if filename.endswith('.py'):
                yiskiDiscord.unload_extension(f'discordCommands.{filename[:-3]}')
                yiskiDiscord.load_extension(f'discordCommands.{filename[:-3]}')
        await ctx.send(embed=embedCreator("Reloaded", "All cogs reloaded", 0x00ad10))
    except Exception as e:
        await ctx.send(embed=embedCreator("Error Reloading", f"`{e}`", 0xbf1300))

yiskiDiscord.run(yiskiConfiguration["yiskiDiscordBotToken"])
