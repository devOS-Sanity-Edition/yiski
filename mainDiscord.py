import os, json5, discord

from discord.utils import async_all

from discord.ext import commands

from loguru import logger

with open("config.json5", "r") as yiskiConfig:
    yiskiConf = json5.load(yiskiConfig)

# Variables so they can be used in other files
githubToken = yiskiConf["githubToken"]
commandPrefix = yiskiConf["yiskiBotPrefix"]
ventChannel = yiskiConf["ventChannelDiscordID"]
ownerRole = yiskiConf["discordOwnerRoleID"]

intents = discord.Intents().all()

yD = commands.Bot(command_prefix=commandPrefix, intents=intents, activity=discord.Activity(type=discord.ActivityType.listening, name="my bones snap"), status=discord.Status.dnd, help_command=None)
logger.add("logs/yiskiDiscord_{time}.log", format="[Yiski Discord][{time:HH:mm:ss}][{level}] {message}", enqueue=True, colorize=True)

def embedCreator(title, desc, color):
    embed = discord.Embed(
        title=f"{title}",
        description=f"{desc}",
        color=color
    )
    return embed

def roleCheck(ctx, role_id):
    userRolesList = []
    for h in list(ctx.message.author.roles):
        userRolesList.append(h.id)
        print(f"{userRolesList}" + f" {h} " + f"{role_id}")
    if role_id in userRolesList:
        print("HHHHHHHHHHHhh")
        return True   
    else:
        print("hhhhhhhhhhiiiiiiiii")
        return False

@yD.event
async def on_ready():
    logger.debug(f"Bots started on Discord's end.")

# Error Handling
@yD.event
async def on_command_error(ctx, error):
    if isinstance(error, commands.CommandNotFound):
        await ctx.send(embed=embedCreator("Unknown Command", f"The command `{ctx.message.content.split(' ')[0]}` is not found! Use `{commandPrefix}help` to list all commands!", 0xbf1300))
        return
    else:
        await ctx.send(embed=embedCreator("Error", f"Unexpected Error: `{error}`", 0xff0000))

@yD.command(aliases=["https://www.youtube.com/watch?v=U9t-slLl30E"])
async def stop(ctx):
    await ctx.send(embed=embedCreator("Stopping", "Shutting Down Yiski", 0xFF0000))
    await yD.close()

# Reloads all commands
@yD.command(aliases=["relaod"])  # this alias is here seriously just because i was tired of speed type misspelling it
async def reload(ctx, extension = None):
    if roleCheck(ctx, yiskiConf["discordOwnerRoleID"]):
        logger.debug("Attempt to reload cogs have started")
        if not extension:
            try:
                for filename in os.listdir('./discordCommands/'):
                    if filename.endswith('.py'):
                        yD.unload_extension(f'discordCommands.{filename[:-3]}')
                        yD.load_extension(f'discordCommands.{filename[:-3]}')
                await ctx.send(embed=embedCreator("Reloaded", "All cogs reloaded", 0x00ad10))
                logger.debug("Attempted reload of cogs successful.")
            except Exception as e:
                await ctx.send(embed=embedCreator("Error Reloading", f"`{e}`", 0xbf1300))
                logger.error(f"Attempted reload of cogs failed, error {e}")
        else:
            try:
                yD.unload_extension(f'discordCommands.{extension}')
                yD.load_extension(f'discordCommands.{extension}')
                await ctx.send(embed=embedCreator(f"Reloaded", f"{extension} has been reloaded.", 0x00ad10))
                logger.debug(f"Attempted reload of {extension} cog successful.")
            except Exception as e:
                await ctx.send(embed=embedCreator(f"Error reloading {extension}", f"{e}", 0xbf1300))
                logger.error(f"Attempted reload of {extension} cog failed, error {e}")
    else:
        await ctx.reply(embed=embedCreator("Insufficient Perms", f"You do not have the required role/permissions to use this command!", 0xFF0000), mention_author=False)

@yD.command()
async def load(ctx, extension):
    if roleCheck(ctx, yiskiConf["discordOwnerRoleID"]):
        yD.load_extension(f'discordCommands.{extension}')
        await ctx.send(embed=embedCreator(f"Loaded", f"{extension} has been loaded.", 0x00ad10))
        logger.debug(f"Attempted load of {extension}")
    else:
        await ctx.reply(embed=embedCreator("Insufficient Perms", f"You do not have the required role/permissions to use this command!", 0xFF0000), mention_author=False)

@yD.command()
async def unload(ctx, extension):
    if roleCheck(ctx, yiskiConf["discordOwnerRoleID"]):
        yD.unload_extension(f'discordCommands.{extension}')
        await ctx.send(embed=embedCreator(f"Unloaded", f"{extension} has been unloaded.", 0x00ad10))
        logger.debug(f"Attempted unload of {extension}")
    else:
        await ctx.reply(embed=embedCreator("Insufficient Perms", f"You do not have the required role/permissions to use this command!", 0xFF0000), mention_author=False)

# load cogs on startup
for filename in sorted(os.listdir('./discordCommands/')):
    if filename.endswith('.py'):
        logger.debug("Loading Cog:" + f'discordCommands.{filename[:-3]}')
        yD.load_extension(f'discordCommands.{filename[:-3]}')

yD.run(yiskiConf["yiskiDiscordBotToken"])