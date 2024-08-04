# Runner

Download <resource src="config.toml"/> and put it in the same folder as your Runner.

config.toml
```Ini
[universal]
githubToken = ""

[discord]
botToken = ""
adminIDs = [ 000000000000000000, 000000000000000000, 000000000000000000 ] # replace this with whoever you want to be able to use administrative commands w/ the bot
homeGuildID = 000000000000000000 # replace this with your discord server's ID
activityType = "LISTENING" # valid types are PLAYING, STREAMING, LISTENING, WATCHING, CUSTOM_STATUS and COMPETING
activityStatus = "" # status text after the type

[database]
host = "localhost"
port = 5432
username = "postgres"
password = "postgres"
db = "yiski"
```

## Universal
`githubToken` 
: Your Personal Access Token from GitHub to be used with the `/gh` command. Generate a Fine-grained token [here](https://github.com/settings/tokens?type=beta)
   - Please only use Fine-grained Tokens, with Repository access of `All Repositories`, and under Repository permissions, allow for read access of
      - Commit statuses
      - Contents
      - Custom properties
      - Issues
      - Metadata
      - Pull Requests

## Discord
`botToken`
: Your Bot Token to allow the bot to sign into Discord

`adminIDs`
: An array of User IDs to allow administrative action of the bot

`homeGuildID`
: Your Server's ID to allow the commands to run on

`activityType`
: Activity type for the bot's status
: Defaults to `LISTENING`

`activityStatus`
: Text that goes after the type's prefix

## Database
`host`
: Hostname/Host IP of the database
: Defaults to `localhost`

`port`
: Port of the database
: Defaults to `5432`

`username`
: Username to connect to the database
: Defaults to `postgres`

`password`
: Password to connect to the database
: Defaults to `postgres`

`db`
: Database name to connect to
: Defaults to `yiski`

