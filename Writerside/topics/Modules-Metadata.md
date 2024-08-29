# Modules Metadata

<primary-label ref="author-asojidev"/>
<secondary-label ref="co-author-deftu"/>
<secondary-label ref="experimental"/>

> The Metadata is still a work in progress, this shows the current utilized draft of Yiski's Module Metadata.
> {style="warning"}

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
> {style="note"}

## Metadata

> Refer to [here](#metadata-changelog) on information about Metadata Versions.
{style="tip"}

`version`
: Config version, **do not EVER** touch unless you know what you're doing.


## Runner

`mainClass`
: Main entrypoint for Runner to pick up and execute code on

## Module

`configClass`
: Config class to allow a module to be configurable through a file

## Packages

> Package paths are optional, but required if you're using anything related to Tables
> {style="note"}

> Do not point these to a file, point it to the package containing the classes/objects.

`databasePackage`
: Your module's package for where all the SQL/Exposed SQL Tables are stored

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

## Metadata Changelog

The metadata at the time of writing is currently not finished, so all versions for now are DRAFT# versions.

**DRAFT1** - Metadata Version: `0`
: Initial implementation
: Implements Metadata table with `version` int field
: Implements Runner table with `mainClass` string field
: Implements Module table with `config` string field
: Implements Packages table with `databasePackage` and `slashCommandsPackage` string field
: Implements Information table with the following fields:
:  - `id` - String
:  - `name` - String
:  - `description` - String
:  - `version` - String, but follow semver
:  - `repo` - String, use link
:  - `license` - String
:  - `authors` - String array, can accept only 1 string in array

**DRAFT2** - Metadata Version: `0`
: Removed `slashCommandsPackage` due to complications with Reflection library, and now the Modules Loader will scan for classes implementing `Scaffold` instead.