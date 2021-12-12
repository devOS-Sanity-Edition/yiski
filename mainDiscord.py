import os, json5, discord

from discord.ext import commands

with open("config.json5", "r") as yiskiConfig:
    yiskiConf = json5.load(yiskiConfig)

# Variables so they can be used in other files
githubToken = yiskiConf["githubToken"]
commandPrefix = yiskiConf["yiskiBotPrefix"]

intents = discord.Intents().all()

yiskiDiscord = commands.Bot(command_prefix=commandPrefix, intents=intents, activity=discord.Activity(type=discord.ActivityType.watching, name="snakes slither"), status=discord.Status.dnd, help_command=None)

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

# Reloads all commands
@yiskiDiscord.command(aliases=["relaod"])  # this alias is here seriously just because i was tired of speed type misspelling it
async def reload(ctx):
    try:
        for filename in os.listdir('./discordCommands/'):
            if filename.endswith('.py'):
                yiskiDiscord.unload_extension(f'discordCommands.{filename[:-3]}')
                yiskiDiscord.load_extension(f'discordCommands.{filename[:-3]}')
        await ctx.send(embed=embedCreator("Reloaded", "All cogs reloaded", 0x00ad10))
    except Exception as e:
        await ctx.send(embed=embedCreator("Error Reloading", f"`{e}`", 0xbf1300))

@yiskiDiscord.command()
async def load(ctx, extension):
    yiskiDiscord.load_extension(f'discordCommands.{extension}')
    await ctx.send(f"loaded {extension}")

# load cogs on startup
for filename in sorted(os.listdir('./discordCommands/')):
    if filename.endswith('.py'):
        yiskiDiscord.load_extension(f'discordCommands.{filename[:-3]}')

yiskiDiscord.run(yiskiConf["yiskiDiscordBotToken"])