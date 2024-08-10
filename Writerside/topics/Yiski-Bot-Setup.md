# Bot Setup
<format style="italic">by asojidev</format>

Want to run Yiski on your own? Well, it's a little bit involved, but here's how to set it up.

> This bot is not yet ready for self-hosting, other than for development purposes, so please shoo for now... please. We'll tell you when it's ready, pinkie promise.
{style="warning"}

## Pre-requisites
Here's what you must have installed before tackling self-hosting this bot.
- A text editor that can handle TOML and YML files
  - You can use JetBrains Fleet, Visual Studio Code, or even Notepad, but we don't recommend the last one solely because it doesn't have formatting.
- Docker
  - Optionally, you may use Podman instead, but you **must** have the Compose plugin installed.
- A brain, the ability to read, and some patience
  - This is pretty important, otherwise... how else are you going to get through this?

## Setup

[//]: # (This whole setup will be covered on both Linux and Windows.)

<tldr>
<p>If you already know how to setup a Discord bot, go ahead and skip this. Just be sure to give it full Administrator privileges, and the Presence, Server Members, and Message Content intent.</p>
</tldr>

### Discord

<procedure title="Discord Setup" id="discord_setup">
    <step>
        <p>Head over to the <a href="https://discord.dev">Discord Developer Portal</a>.</p>
        <img src="setup_0.png" alt=""/>
    </step>
    <step>
        <p>Click on Applications on the top left.</p>
        <img src="setup_1.png" alt=""/>
    </step>
    <step>
        <p>Sign in to your Discord account if you haven't already.</p>
        <img src="setup_2.png" alt=""/>
    </step>
    <step>
        <p>You will be redirected to your Applications page after signing in.</p>
        <img src="setup_3.png" alt=""/>
    </step>
    <step>
        <p>Click on New Application on the top right.</p>
        <img src="setup_4.png" alt=""/>
    </step>
    <step>
        <p>In the <b>Create an Application</b> modal, fill out how you want it.</p>
        <list>
            <li>
                <p>We'll call it Yiski and put it under our Personal account here.</p>
            </li>
        </list>
        <img src="setup_5.png" alt=""/>
        <list>
            <li>
                <p>It won't really matter if it's as a Team or Personal, unless you want other people managing your bot's application.</p>
            </li>
            <li>
                <p>Make sure to agree to the Discord Developers Terms of Service and Developer Policy checkbox.</p>    
            </li>
        </list>
    </step>
    <step>
        <p>You will be redirected to your Application's page. Here you will be able to manage your Application.</p>
        <img src="setup_6.png"/>
    </step>
    <step>
        <p>Click on Bots on the sidebar</p>
        <img src="setup_7.png" alt=""/>
    </step>
    <step>
        <p>You will now be at the Bots page, here you can setup your bot's profile, get the token, setup intents, and have a permissions calculator.</p>
        <img src="setup_8.png" alt=""/>
    </step>
    <step>
        <p>Click on Reset Token</p>
        <img src="setup_9.png" alt=""/>
        <warning>Do not share this Token, ever! Only share it if you know what you're doing. None of the Yiski devs will ever ask for your Bot's Token.</warning>
        <list>
            <li>
                <p>If prompted, type in your 2FA code to reset the bot token.</p>
            </li>
        </list>
    </step>
    <step>
        <p>You now have your bot token! Store it somewhere temporarily, maybe in a temporary Notepad window, we'll need this later. We'll be using this to have the bot login.</p>
    </step>
    <step>
        <p>Scroll down a little, and enable <b>Presence Intent</b>, <b>Server Members Intent</b>, and <b>Message Content Intent</b>.</p>
        <img src="setup_10.png" alt=""/>
    </step>
    <step>
        <p>Next, head over to the Installation page.</p>
        <img src="setup_11.png" alt=""/>
    </step>
    <step>
        <p>You will now be at the Installations page. Here you can get your bot into your server [guild], or install as an app. We'll want to do a Guild Install.</p>
        <img src="setup_12.png" alt=""/>
    </step>
    <step>
        <p>Scroll down until you find Guild Install under Default Install Settings. Click on the Dropdown and click on <b>bot</b></p>
        <img src="setup_13.png" alt=""/>
    </step>
    <step>
        <p>The Guild Install option will now expand. Click on Select for Permission and select <b>Administrator</b>.</p>
        <img src="setup_14.png" alt=""/>
    </step>
    <step>
        <p>Click on <b>Save Changes</b></p>
        <img src="setup_15.png" alt=""/>
    </step>
    <step>
        <p>Scroll back up until you find Install Link. Make sure it's selected on <b>Discord Provided Link</b>. Click on Copy.</p>
        <img src="setup_16.png" alt=""/>
    </step>
    <step>
        <p>Open a new tab, and paste the link you got, and go to it. Select on <b>Add to Server</b></p>
        <img src="setup_17.png" alt=""/>
    </step>
    <step>
        <p>On the External Applications screen, select what server you want to put this bot into under the <b>Add to Server</b> dropdown. Click <b>Continue</b> once you have your server selected.</p>
        <img src="setup_18.png" alt=""/>
    </step>
    <step>
        <p>The prompt will now ask you to confirm if you want to add this bot with the permissions specified. Hit <b>Authorize</b>.</p>
        <img src="setup_19.png" alt=""/>
    </step>
    <step>
        <p>Your bot will now be added to your server.</p>
        <img src="setup_20.png" alt=""/>
    </step>
</procedure>

<procedure title="Docker Compose Install" id="docker-compose-install">
    <warning>Sarcasm ahead.</warning>
    <step>
        <p>Go to where you want to install your bot</p>
    </step>
    <step>
        <p>Now go step on a LEGO because this bot isn't production ready enough yet to be self hosted. This will be updated when it's ready to.</p>
    </step>
</procedure>