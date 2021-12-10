from defectio.ext import commands

yiski = commands.Bot(command_prefix="/")

@yiski.event
async def on_ready():
    print("howdy")

@yiski.command()
async def hello(ctx):
    await ctx.reply("howdy", mention = True)

@yiski.command()
async def httpcat(ctx, httpcode: str):
    await ctx.reply("[cat](https://http.cat/" + httpcode + ")")

yiski.run("Fkf_adLmBvlI_IGRjzF8VN-it6eC7At-NLJadYgOhu3OaQ4Q6pQOaZpqduuSs_Dn")