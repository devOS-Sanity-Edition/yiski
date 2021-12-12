import defectio, requests
import json5
from defectio import ext
from defectio.ext import commands

from mainRevolt import githubToken


class GHRRevolt(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    @commands.command()
    async def ghr(self, ctx, name: str, repo: str):
        if githubToken != None:
            headers = {"Authorization": "token " + githubToken}
            request = requests.get(f"https://api.github.com/repos/{name}/{repo}", headers=headers)
        else:
            request = requests.get(f"https://api.github.com/repos/{name}/{repo}")

        if request.status_code == 200:
            if request.json()["description"] != None:
                githubResponseDesc = request.json()["description"] + f"[Link](https://www.github.com/{name}/{repo})"
            else:
                githubResponseDesc = f"Sadly, there's not a description for this repository."

            githubResponseLang = request.json()["Language"]

            if request.json()["license"] != None:
                githubResponseLicense = request.json()["license"]["spdx_id"]
            else:
                githubResponseLang = "None"

            githubResponseStars = request.json()["stargazers_count"]
            githubResponseForks = request.json()["forks_count"]
            githubResponseWatchers = request.json()["watchers_count"]
            githubResponseOpenIssues = request.json()["open_issues_count"]
            githubResponseOwner = request.json()["owner"]["avatar_url"]

            await ctx.reply(f"| Repo Owner and Repo Name | {name}/{repo}              |\n" +
                            f"| ------------------------ | -------------------------- |\n" +
                            f"| Description              | {githubResponseDesc}       |\n" +
                            f"| Language                 | {githubResponseLang}       |\n" +
                            f"| License                  | {githubResponseLicense}    |\n" +
                            f"| Stars                    | {githubResponseStars}      |\n" +
                            f"| Forks                    | {githubResponseForks}      |\n" +
                            f"| Open Issues              | {githubResponseOpenIssues} |\n" +
                            f"| Watchers                 | {githubResponseWatchers}   |\n", mention=True)

        elif request.status_code == 404:
            await ctx.reply(f"Sorry, your repository of `{name}/{repo}` couldn't be found.", mention=True)
        elif request.status_code == 403:
            if githubToken != None:
                await ctx.reply("# Seems like you got Rate Limited by GitHub.\n"
                                "## Is your GitHub Token currently being used? \n\n" +
                                "Yeah, seems like so. Please make sure that you have a valid token, or contact the "
                                "bot owner to make sure that they have a valid token.\nOr that you somehow managed "
                                "exceeded the rate limit with one of those?????")
            else:
                await ctx.reply("# Is your GitHub Token currently being used? \n\n" +
                                "Doesn't look like so. Use a GitHub Token in your config.json5, or contact the bot "
                                "owner to get one put into config.json5!\n You will not get ratelimited as often!")
        else:
            await ctx.reply(f"Unknown Error. [Here's a cat regarding your error.](https://http.cat/{request.status_code})")


def setup(bot: defectio.ext.commands.Bot):  # actually register the command
    bot.add_cog(GHRRevolt(bot))
