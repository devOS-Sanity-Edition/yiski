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
async def reload(ctx, extension = None): # ftr, `extension: None` doesn't work, but `extension = None` does, so that's nice lol.
    logger.debug("Attempt to reload cogs have started")
    if not extension:
        try:
            for filename in os.listdir('./revoltCommands/'):
                if filename.endswith('.py'):
                    yiskiRevolt.unload_extension(f'revoltCommands.{filename[:-3]}')
                    yiskiRevolt.load_extension(f'revoltCommands.{filename[:-3]}')
            await ctx.reply("# Reloaded\n" + "All cogs have been reloaded.", mention=True)
            logger.debug("Attempted reload of cogs successful.")
        except Exception as e:
            await ctx.reply("# Cogs Reload Failed.\n" + f"Error: {e}", mention=True)
            logger.error(f"Attempted reload of cogs failed, error {e}")
    else:
        try:
            yiskiRevolt.unload_extension(f'revoltCommands.{extension}')
            yiskiRevolt.load_extension(f'revoltCommands.{extension}')
            await ctx.reply("# Reloaded\n" + f"{extension} has been reloaded.", mention=True)
            logger.debug(f"Attempted reload of {extension} cog successful.")
        except Exception as e:
            await ctx.reply(f"# {extension} Reload Failed.\n" + f"Error {e}", mention=True)
            logger.error(f"Attempted reload of {extension} cog failed, error {e}")

@yiskiRevolt.command()
async def load(ctx, extension):
    yiskiRevolt.load_extension(f'revoltCommands.{extension}')
    logger.debug(f"Attempted load of {extension}")
    await ctx.reply(f"Loaded {extension}", mention=True)

@yiskiRevolt.command()
async def unload(ctx, extension):
    yiskiRevolt.unload_extension(f'revoltCommands.{extension}')
    logger.debug(f"Attempted unload of {extension}")
    await ctx.reply(f"Unloaded {extension}", mention=True)

yiskiRevolt.run(yiskiConf["yiskiBotToken"])
