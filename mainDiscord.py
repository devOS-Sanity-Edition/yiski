import os, json5, discord
from discord.utils import async_all

from discord.ext import commands

with open("config.json5", "r") as yiskiConfig:
    yiskiConf = json5.load(yiskiConfig)

# Variables so they can be used in other files
githubToken = yiskiConf["githubToken"]
commandPrefix = yiskiConf["yiskiBotPrefix"]
ventChannel = yiskiConf["ventChannelDiscordID"]

intents = discord.Intents().all()

yiskiDiscord = commands.Bot(command_prefix=commandPrefix, intents=intents, activity=discord.Activity(type=discord.ActivityType.listening, name="my bones snap"), status=discord.Status.dnd, help_command=None)

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

# Error Handling
@yiskiDiscord.event
async def on_command_error(ctx, error):
    if isinstance(error, commands.CommandNotFound):
        await ctx.send(embed=embedCreator("Unknown Command", f"The command `{ctx.message.content.split(' ')[0]}` is not found! Use `{commandPrefix}help` to list all commands!", 0xbf1300))
        return
    else:
        await ctx.send(embed=embedCreator("Error", f"Unexpected Error: `{error}`", 0xff0000))

@yiskiDiscord.command(aliases=["https://www.youtube.com/watch?v=U9t-slLl30E"])
async def stop(ctx):
    await ctx.send(embed=embedCreator("Stopping", "Shutting Down Yiski", 0xFF0000))
    await yiskiDiscord.close()
# Reloads all commands
@yiskiDiscord.command(aliases=["relaod"])  # this alias is here seriously just because i was tired of speed type misspelling it
async def reload(ctx, extension = None):
    if not extension:
        try:
            for filename in os.listdir('./discordCommands/'):
                if filename.endswith('.py'):
                    yiskiDiscord.unload_extension(f'discordCommands.{filename[:-3]}')
                    yiskiDiscord.load_extension(f'discordCommands.{filename[:-3]}')
            await ctx.send(embed=embedCreator("Reloaded", "All cogs reloaded", 0x00ad10))
        except Exception as e:
            await ctx.send(embed=embedCreator("Error Reloading", f"`{e}`", 0xbf1300))
    else:
        try:
            yiskiDiscord.unload_extension(f'discordCommands.{extension}')
            yiskiDiscord.load_extension(f'discordCommands.{extension}')
            await ctx.send(embed=embedCreator(f"Reloaded", f"{extension} has been reloaded.", 0x00ad10))
        except Exception as e:
            await ctx.send(embed=embedCreator(f"Error reloading {extension}", f"{e}", 0xbf1300))

@yiskiDiscord.command()
async def load(ctx, extension):
    yiskiDiscord.load_extension(f'discordCommands.{extension}')
    await ctx.send(embed=embedCreator(f"Loaded", f"{extension} has been loaded.", 0x00ad10))

@yiskiDiscord.command()
async def unload(ctx, extension):
    yiskiDiscord.unload_extension(f'discordCommands.{extension}')
    await ctx.send(embed=embedCreator(f"Unloaded", f"{extension} has been unloaded.", 0x00ad10))

# load cogs on startup
for filename in sorted(os.listdir('./discordCommands/')):
    if filename.endswith('.py'):
        yiskiDiscord.load_extension(f'discordCommands.{filename[:-3]}')

yiskiDiscord.run(yiskiConf["yiskiDiscordBotToken"])