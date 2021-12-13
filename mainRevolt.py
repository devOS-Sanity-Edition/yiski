import sys, os, json5, defectio, loguru

from loguru import logger
from defectio.ext import commands

with open("config.json5", "r") as yiskiConfig:
    yiskiConf = json5.load(yiskiConfig)

# Variables so they can be used in other files
githubToken = yiskiConf["githubToken"]
commandPrefix = yiskiConf["yiskiBotPrefix"]

yiskiRevolt = defectio.ext.commands.Bot(command_prefix=commandPrefix, help_command=None)
logger.add("logs/yiskiRevolt_{time}.log", format="[Yiski Revolt][{time:HH:mm:ss}][{level}] {message}", enqueue=True, colorize=True)

# load cogs on startup
for filename in sorted(os.listdir('./revoltCommands/')):
    if filename.endswith('.py'):
        logger.debug("Loading Cog:" + f'revoltCommands.{filename[:-3]}')
        yiskiRevolt.load_extension(f'revoltCommands.{filename[:-3]}')


@yiskiRevolt.event
async def on_ready():
    logger.debug("Bots started on Revolt end.")


@yiskiRevolt.event
async def on_command_error(ctx, error):
    if isinstance(error, commands.MissingRequiredArgument):
        await ctx.reply(
            f"Hm... looks like this isn't valid syntax? Are you sure you followed the proper syntax for this command? If you need help, refer to `{commandPrefix}help`. If you think this is an error, ping Devin!",
            mention=True)


# Reloads all commands
@yiskiRevolt.command(aliases=["relaod"])  # this is here seriously just because i was tired of speed type misspelling it
async def reload(ctx):
    logger.debug("Attempt to reload cogs have started")
    try:
        for filename in os.listdir('./revoltCommands/'):
            if filename.endswith('.py'):
                yiskiRevolt.unload_extension(f'revoltCommands.{filename[:-3]}')
                yiskiRevolt.load_extension(f'revoltCommands.{filename[:-3]}')
        await ctx.send("# Reloaded\n" + "All cogs have been reloaded.")
        logger.debug("Attempted reload of cogs successful.")
    except Exception as e:
        await ctx.send("# Cogs Reload Failed.\n" + f"Error: {e}")
        logger.error(f"Attempted reload of cogs failed, error {e}")


yiskiRevolt.run(yiskiConf["yiskiBotToken"])
