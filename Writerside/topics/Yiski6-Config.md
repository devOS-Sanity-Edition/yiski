# Yiski6

<primary-label ref="author-asojidev"/>

> This exists as a seperate bot at the moment, but hasn't been ported yet. Support will be limited. No documentation provided other than what's in the file as comments.
{style="warning"}

Currently empty for now as work on porting Yiski6 has not started yet.

Old Yiski6 config.toml, will be revised for the port
```Ini
[bot]
# The bots Discord token.
token = "<discord token>"
# What activity type should be shown reference https://ci.dv8tion.net/job/JDA5/javadoc/net/dv8tion/jda/api/entities/Activity.ActivityType.html
activity = "LISTENING"
# The satus to be shown in Discord after the activity, example: "Listening to <your status here>"
status = "your messages"
# Your Discord ID, this isn't used for anything at the moment.
developers = [ ]
# Whether text-to-speach should be enabled by default for users.
enabledByDefault = true
# Should the bot leave the channel once everyone leaves?
leaveVoiceChannelAutomatically = true

# Channel and user settings storage, reference https://github.com/JetBrains/Exposed/wiki/DataBase-and-DataSource#datasource
# We include the h2 and postgres driver by default, for other drivers you will need to build this yourself.
[database]
# The driver to be used for the database.
driver = "org.h2.Driver"
# In this case, this is the path for the h2 database <yiski.mv.db>
url = "jdbc:h2:./yiski"
```