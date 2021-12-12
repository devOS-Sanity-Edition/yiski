from discord.ext import commands
import requests

from mainDiscord import embedCreator, githubToken


class FunDiscord(
    commands.Cog):  # this defines what "class" things will be in, can be completely custom, ie Util, Admin, etc.
    def __init__(self, client):
        self.client = client

    @commands.command()  # says its a command
    async def ghr(self, ctx, name: str, repo: str):  # self always needs to be before ctx in cogs

        headers = {"Authorization": "token " + githubToken}
        request = requests.get(f"https://api.github.com/repos/{name}/{repo}", headers=headers)

        if request.status_code == 200:

            embed=embedCreator(f"{name}/{repo}", "", 0x00ff00)
            try:
                embed.add_field(name="Description", value=request.json()["description"] + f"\n[Link](https://www.github.com/{name}/{repo})", inline=False)
            except TypeError:
                embed.add_field(name="Description", value=f"No description \n[Link](https://www.github.com/{name}/{repo})", inline=False)

            embed.add_field(name="Language", value=request.json()["language"])
    
            try:
                embed.add_field(name="License", value=request.json()["license"]["spdx_id"])
            except TypeError:
                embed.add_field(name="License", value="None")
        
            embed.add_field(name="Stars", value=request.json()["stargazers_count"])
            embed.add_field(name="Forks", value=request.json()["forks_count"])
            embed.add_field(name="Watchers", value=request.json()["watchers_count"])
            embed.add_field(name="Open Issues", value=request.json()["open_issues_count"])
            embed.set_thumbnail(url=request.json()["owner"]["avatar_url"])
        
        elif request.status_code == 404:
            embed=embedCreator("Error", "That Repository is not found. Check your spelling or make sure that repo exists", 0xFF0000)
        else:
            embed=embedCreator("Error", f"HTTP Status Code: {request.status_code}", 0xFF0000)

        await ctx.send(embed=embed)


def setup(client):  # actually register the command
    client.add_cog(FunDiscord(client))  # add the cog, you need to use the same thing in the cog as the class above
