# Modules Metadata
<format style="italic">by asojidev</format>

> The Metadata is still a work in progress, this shows the current utilized draft of Yiski's Module Metadata.
{style="warning"}

Every module needs its own Metadata file, a file that contains information about what to load.
Specifically, it tells the Runner to run the module's respective config, PostgresQL tables, slash commands, and
information about itself.

<tabs>
<tab title="Empty">
<p>yiski.metadata.toml</p>
<br/>
<code-block lang="ini" src="yiski.metadata.example.toml"/>
</tab>
<tab title="Filled">
<p>yiski.metadata.toml</p>
<br/>
<code-block lang="ini" src="yiski.metadata.toml"/>
</tab>
</tabs>

> Assume all fields here are required unless specified otherwise.
{style="note"}

## Metadata

`version`
: Config version, **do not EVER** touch unless you know what you're doing.

## Runner

`mainClass`
: Main entrypoint for Runner to pick up and execute code on

## Module

`configClass`
: Config class to allow a module to be configurable through a file

### Packages

> Package paths are optional, but required if you're using anything related to Tables or Slash Commands
{style="note"}

> Do not point these to a file, point it to the package containing the classes/objects.

`databasePackage`
: Your module's package for where all the SQL/Exposed SQL Tables are stored

`slashCommandsPackage`
: Your module's package for where all the Slash Commands are stored

## Information

`id`
: The ID for your module

`name`
: The Name of your module

`description`
: What your module does/add

`version`
: Version of your module

`repo`
: Link to the repository where your module is

`license`
: The License your module uses

`authors`
: An array of names of people who have contributed to the module

<seealso>
    <!--Provide links to related how-to guides, overviews, and tutorials.-->
</seealso>