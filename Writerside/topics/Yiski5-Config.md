# Yiski5

Download <resource src="yiski5.config.toml"/> and put it in the same folder as your Runner.

yiski5.config.toml
```Ini
[bot] # Essential settings for the bot.
# Timezone your are basing the reset on.
timezone = "America/Los_Angeles"
# How many days into the future do you want this to initially start on, if doing a midnight
# I recommend 1 (else you select the previous and it starts instantly)
daysAhead = 1
# Interval you want the bot to be reset at in minutes.
resetInterval = 1440
# Time you want the initial reset to occor at, in 24 hour time.
initialResetHour = 23
initialResetMinute = 59

[channels] # Channel related settings.
# Your vent channel ID.
vent = "000000000000000000"
# The channel ID for vent logs to be sent to.
ventLog = "000000000000000000"
# The channel ID for vent attachments (uploaded files) to be sent to.
ventAttachments = "000000000000000000"

[filters] # Message types the bot will filter out of the wipe (true = not removed).
# Webhook messages.
webhooks = false
# Bot messages.
bots = false
# System messages.
system = true
# Pinned messages.
pinned = true
# A list of message IDs to not be removed. Use if you have any messages to be never wiped.
messages = [ 000000000000000000, 000000000000000000 ]
# A list of user IDs to not remove the messages of.
authors = [ 000000000000000000 ]
```

## Bot
`timezone`
: Timezone you're basing your reset on. Use the [TZ Identifier](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones#Time_zone_abbreviations) to figure out your timezone to input
: Defaults to `America/Los_Angeles`

`daysAhead`
: How many days in the future do you want this module to initially start on, if doing a midnight.
: 1 is recommended, or else it selects the previous days and starts the vent wipe instantly.
: Defaults to `1`

`resetInterval`
: Interval you want the channel to be reset in minutes
: Defaults to `1440` [24h]

`initialResetHour` / `initialResetMinute`
: Time you want the initial reset to occur at, in 24 hour time.
: Defaults to `23` / `59`

## Channels
`vent`
: Your Vent Channel ID
: Defaults to `"000000000000000000"`

`ventLog`
: The channel ID for vent logs to be sent to.
: Defaults to `"000000000000000000"`

`ventAttachments`
: The channel ID for vent attachments (uploaded files) to be sent to.
: Defaults to `"000000000000000000"`

## Filters

> Message types the bot will filter out of the wipe (true = not removed).
{style="note"}

`webhooks`
: Webhook messages
: Defaults to `false`

`bots`
: Bot messages
: Defaults to `false`

`system`
: System messages
: Defaults to `true`

`pinned`
: Pinned messages
: Defaults to `true`

`messages`
: A list of message IDs to not be removed. Use if you have any messages to be never wiped.
: Defaults to `[ 000000000000000000, 000000000000000000 ]`

`authors`
: A list of user IDs to not remove the messages of.
: Defaults to `[ 000000000000000000 ]`