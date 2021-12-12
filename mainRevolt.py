import os
import json5

# Revolt Python Lib
import defectio
from defectio.ext import commands

with open("config.json5", "r") as yiskiConfig:
    yiskiConfiguration = json5.load(yiskiConfig)

commandPrefix = yiskiConfiguration["yiskiBotPrefix"]

yiskiRevolt = defectio.ext.commands.Bot(command_prefix=commandPrefix, help_command=None)

# load cogs on startup
for filename in sorted(os.listdir('./revoltCommands/')):
    if filename.endswith('.py'):
        yiskiRevolt.load_extension(f'revoltCommands.{filename[:-3]}')



@yiskiRevolt.event
async def on_ready():
    print("howdy from Revolt")


@yiskiRevolt.event
async def on_command_error(ctx, error):
    if isinstance(error, commands.MissingRequiredArgument):
        await ctx.reply(
            f"Hm... looks like this isn't valid syntax? Are you sure you followed the proper syntax for this command? If you need help, refer to `{commandPrefix}help`. If you think this is an error, ping Devin!",
            mention=True)


# Reloads all commands
@yiskiRevolt.command(
    aliases=["relaod"])  # this is here seriously just because i was tired of speed type misspelling it
async def reload(ctx):
    try:
        for filename in os.listdir('./revoltCommands/'):
            if filename.endswith('.py'):
                yiskiRevolt.unload_extension(f'revoltCommands.{filename[:-3]}')
                yiskiRevolt.load_extension(f'revoltCommands.{filename[:-3]}')
        await ctx.send("# Reloaded\n" + "All cogs have been reloaded.")
    except Exception as e:
        await ctx.send("# Cogs Reload Failed.\n" + f"Error: {e}")


yiskiRevolt.run(yiskiConfiguration["yiskiRevoltBotToken"])
