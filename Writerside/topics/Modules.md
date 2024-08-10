# Modules
<format style="italic">by Deftu</format>

Each module must provide its own implementation of `YiskiModuleEntrypoint` and `ConfigSetupEntrypoint`, and then provide the full path to both implementations in their respective fields of its `yiski.metadata.toml`.

## Creating your main entrypoint

Implementations of `YiskiModuleEntrypoint` **must** take a `DatabaseManager`, `Aviation`, `JDA` and `AbstractYiskiConfig` (in that order) in their constructor.

The `setup` function from the entrypoint can be used for listening to JDA/Aviation events, checking properties in the module's entrypoint, etc. It's good practice to perform any Discord-relating logic inside a listener for `ReadyEvent`.

## Creating your config setup entrypoint

No constructor arguments are passed down to `ConfigSetupEntrypoint`s, making their implementation very simple. Your module **must** provide some kind of implementation of `AbstractYiskiConfig`, that being the object which contains config data. The `read` function itself does not return anything, instead `ConfigSetupEntrypoint` opts for you to use that function to set the `config` property found inside of your implementation.

<seealso style="cards">
    <category ref="related-modules-data" >
        <a href="Modules-Metadata.md" summary="Take a look at how the Modules Metadata is done and its relevancy."></a>
    </category>
</seealso>