# What is Yiski?

<img src="yiski_banner.png" alt="Yiski Logo" height="300"></img>

Yiski is an in-house developed Discord bot by devOS: Sanity Edition, with its development being lead by [asojidev](https://github.com/asoji).

There have been previous bots before this made by different people in the devOS community, but they have had their issues, and sometimes other solutions don't work out as well as we want them to.

The main point in Yiski is to have an in-house developed bot that satisfies our needs and/or wants. This included a Vent Channel wiper [Yiski5], a decent-enough TTS solution [Yiski6], or even just small fun commands [Yiski3].

## How did the Yiski project come to be?

Originally there was a few custom bots throughout devOS's origins, but one notable one was called `devOS Services`. Its primary purpose was Vent Channel Wiping and some Moderation commands. Let's just say things went downhill for a little, and increasingly got worse, so we eventually threw it out.

Yiski4 was the first custom devOS bot that actually ran for a bit, but all it did was serve system status for asojidev's Raspberry Pi 4. Eventually Yiski itself came along but only really as a fun bot with quirky little commands that threw images, videos, and a bit more.
All of that was built on pycord, right around the time discordpy was discontinued for the time being.

A few rewrites later, here comes this attempt, built to be an All-In-One bot, primarily Moderation, Fun, Vent Channel Wiping, TTS, and probably more. We hope this time around it'll work out, but who knows!

## What is up with the numbers ahead of Yiski? and why the name?

Those are different modules of Yiski, previously all seperate bots, but now merged into 1, sort of. How it all started was Yiski4, a bot originally meant to just monitor the system resources of asoji's Raspberry Pi 4.

How the Yiski name came about is a combination of `Yikes` and `Pi 4`, for the original Yiski4 bot, which is not exactly great, but it worked for the time being. The name just stuck though, so we're forever left with Yiski.

![yiski_name_origin.png](yiski_name_origin.png)

Other than being seperate modules, the numbers have no actual real meaning, they're basically picked in random or whatever came after idea-wise at the time.

## How is Yiski being tackled now?

The bot is now split up in modules, with a Main Runner, a Common API [really just shared utilities], Metadata, and the modules themselves. The idea would be that each module serves their own purpose and tasks, like a mini-bot sort of thing, and it can all use a Common API, and all be run under 1 Runner process, with a Modules Loader to load all the modules under the runner.

The entirety of the bot is run on top of [JDA](https://github.com/discord-jda/JDA), [JDA-KTX](https://github.com/minndevelopment/jda-ktx), and [CephalonCosmic](https://twitter.com/CephalonCosmic)'s [Aviation](https://gitlab.com/artrinix/discord/aviation) Framework. The Module Loader and Metadata Loader is developed by [Deftu](https://github.com/Deftu). All of Yiski's code can be found [here](https://github.com/devOS-Sanity-Edition/Yiski).

## Is this a complete project?

No, and to be honest, it probably never will be a fully complete project, but it can always improve and maybe one day hit a 1.0 release. It currently however is still unfinished and not fully production ready, since the modules are still being actively worked on, with no real stable API, so use the bot at your own discretion.
