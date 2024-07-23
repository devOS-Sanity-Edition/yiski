# Yiski Plugin Loader Metadata

WIP

## Idea
During talks of working on a potential module plugin loader, the idea came up to give each module plugin its own 
metadata file for the plugin loader to easily read and work off of. This file jumps off the idea of Fabric's FMJ 
[Fabric Mod JSON] metadata file that all Fabric mods use.

All example code can be seen in [here](yiskimodule), and just the TOML file itself can be seen 
[here](yiski.metadata.toml).

## Proposed implementation

Have it as a TOML file named `yiski.metadata.toml` under a module's `resources` folder, with the following config.

```toml
[metadata]
version = 0

[runner]
mainClass = ""

[module]
configClass = ""
    [module.packages]
    databasePackage = ""
    slashCommandsPackage = ""

[information]
id = ""
name = ""
description = ""
version = ""
repo = ""
license = ""
authors = [
    ""
 ]
```

Here's the breakdown of it all

```toml
# Yiski Metadata TOML Draft
# This draft TOML file is what's being thought up of, to put in every module plugin for some basic information for the
# initialization in the upcoming Yiski Plugin Loader, and other nice information, like module name and description.
# All code for a module should be expected to be Kotlin code, and ideally only Kotlin code.
# If you want to try Java, be my guest.
#
# This draft is developed by asojidev, and Deftu.

# Place this file in your module's resource directory, like so
#
# ModuleName
# └───src
#     └───main
#         ├───kotlin
#         │   └───author.module
#         │       ├───BasicModule.kt
#         │       ├───BasicModuleConfig.kt
#         │       └───data
#         │           └───BasicModuleConfigData.kt
#         └───resources
#             └───yiski.metadata.toml

[metadata] # Any sort of information for the metadata
version = 0 # Do not touch this under any circumstance unless you know what you're doing or else I'm personally going to
            # visit your home, steal your cat, and bite the fucking wall.
            #
            # 0 = DRAFT/UNIMPLEMENTED/WORK IN PROGRESS
            # 1 = FIRST FINAL VERSION
            # # = WHATEVER THE FUCK HAPPENS AFTER THIS I DON'T KNOW YET

[runner] # Any sort of information for the Yiski Runner to load.
mainClass = "" # Point directly to your class that uses YiskiModuleEntrypoint.

[module] # Any sort of information about the module's code.
configClass = "" # Point directly to your config initializer class.
    [module.packages] # Any sort of information about the module's packages.
    databasePackage = "" # Optional, point directly to your Database package if you have database tables.
    slashCommandsPackage = "" # Optional, point directly to your Slash Commands package if you have slash commands.

[information]
id = "" # Named ID of your Yiski Module
name = "" # Name of your Yiski Module.
description = "" # What does your module do? describe that in short detail.
version = "" # Optional, what version is your module? This will fallback to the Yiski project version if not supplied.
repo = "" # Optional, link to your module's source code repo
license = "" # License of your module [ex: MIT, LGPLv3, ARR]
authors = [ # List of authors/people that have contributed to the module. At least 1 string is required.
    ""
 ]
```

Here's an example of what an actual filled in module metadata could look like.

```toml
[metadata]
version = 0

[runner]
mainClass = "one.devos.basicmodule.BasicModule"

[module]
configClass = "one.devos.basicmodule.BasicModuleConfig"
    [module.packages]
    databasePackage = "one.devos.basicmodule.database"
    slashCommandsPackage = "one.devos.basicmodule.commands"

[information]
id = "basicmodule"
name = "Basic Module"
description = "A module that just basically exists like your mother"
version = "0.1.0"
repo = "https://github.com/devOS-Sanity-Edition/yiski/tree/aviation/drafts/plugin-loader-metadata/"
license = "MIT"
authors = [
    "asojidev",
    "CephalonCosmic"
]
```