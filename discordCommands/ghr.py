from discord.ext import commands
from loguru import logger
import requests

from mainDiscord import embedCreator, githubToken


class GHRDiscord(
    commands.Cog):
    def __init__(self, client):
        self.client = client

    @commands.command()
    async def ghr(self, ctx, name: str, repo: str):

        if githubToken != None:
            headers = {"Authorization": "token " + githubToken}
            request = requests.get(f"https://api.github.com/repos/{name}/{repo}", headers=headers)
        else:
            request = requests.get(f"https://api.github.com/repos/{name}/{repo}")

        if request.status_code == 200:

            embed = embedCreator(f"{name}/{repo}", "", 0x00ff00)
            if request.json()["description"] != None:
                embed.add_field(name="Description",
                                value=request.json()["description"] + f"\n[Link](https://www.github.com/{name}/{repo})",
                                inline=False)
            else:
                embed.add_field(name="Description",
                                value=f"No description \n[Link](https://www.github.com/{name}/{repo})", inline=False)

            embed.add_field(name="Language", value=request.json()["language"])
            if request.json()["license"] is not None:
                embed.add_field(name="License", value=request.json()["license"]["spdx_id"])
            else:
                embed.add_field(name="License", value="None")
            embed.add_field(name="Stars", value=request.json()["stargazers_count"])
            embed.add_field(name="Forks", value=request.json()["forks_count"])
            embed.add_field(name="Watchers", value=request.json()["watchers_count"])
            embed.add_field(name="Open Issues", value=request.json()["open_issues_count"])
            embed.add_field(name="Visibility", value=request.json()["visibility"])
            embed.set_thumbnail(url=request.json()["owner"]["avatar_url"])


        elif request.status_code == 404:
            embed = embedCreator("Error",
                                 "That Repository is not found. Check your spelling or make sure that repo exists",
                                 0xFF0000)
        elif request.status_code == 403:
            embed = embedCreator("Error", f"You Have Exceeded the GitHub API Rate Limit", 0xFF0000)
            if githubToken != None:
                embed.add_field(name="Is a GitHub Token Being Used?",
                                value="Yes...\nPlease make sure that you have a valid token, or contact the bot owner "
                                      "to make sure that they have a valid token.\nOr that you somehow managed "
                                      "exceeded the rate limit with one of those?????")
            else:
                embed.add_field(name="Is a GitHub Token Being Used?",
                                value="No...\nUse a GitHub Token in your config.toml, or contact the bot owner to "
                                      "get one put into config.toml!\n You will not get ratelimited as often!")
        else:
            embed = embedCreator("Error", f"Unknown Error, Status Code: {request.status_code}", 0xFF0000)

        await ctx.send(embed=embed)


def setup(client):
    client.add_cog(GHRDiscord(client))
    logger.debug("GHR Cog loaded.")
