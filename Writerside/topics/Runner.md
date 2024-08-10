# Yiski's Main Runner
<format style="italic">by Deftu</format>

The bot's launch entrypoint, or main runner, is responsible for initially setting up JDA, database management, etc. 
Of course, it is also responsible for managing each of the bot's modules using the module loading system.

## Initial startup

In order of its functionality:

- It will first discover and load any available modules,
- Then extract any necessary initial startup entrypoints (these being the `ConfigSetupEntrypoint` and the `YiskiModuleEntrypoint`).
- Available `ConfigSetupEntrypoint`s will then be read and validated to ensure that all config files are correct.
- Each module's database package will then have all of its tables registered with Exposed.
- Finally, we can now begin setting up JDA and Aviation. Inside which, all modules will have their slash commands registered.
- After we have a functioning instance of JDA, we can then inform each of our modules that it's time for setup. **At this stage, we are unaware of if JDA was able to log in. The setup function call is not done in alignment with JDA's `ReadyEvent`.**

## Consequent interference

After the main runner has done its purpose in allowing the modules to interface with its centralized instance of JDA, it will not be interfering with the functionality of the bot any further. The only events the main runner listens to inside of JDA are for logging out the bot's current online state and syncing commands in **test guilds**.

<seealso style="cards">
    <category ref="modules">
        <a href="Modules-Loader.md" summary="How the Module Loader works"></a>
        <a href="Modules.md" summary="How Modules work"></a>
    </category>
</seealso>