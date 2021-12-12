from discord.ext import commands


class Fun(commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def gasp(self, ctx):
        await ctx.reply("😮\n\n https://youtu.be/GwIlt8pJdHg",
                        mention_author=True)


def setup(client):
    client.add_cog(Fun(client))
