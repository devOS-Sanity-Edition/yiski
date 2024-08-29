# About Configs

<primary-label ref="author-asojidev"/>

## Why are these configs needed?

### Short Answer:

<tldr>
Each modules does different things and has different values, best to provide each module with its own file for its own values.
</tldr>

### Long answer:
Each seperate module has its own configuration file, due to how they work, it's best to not merge all the configurations all into one file.
Having a seperate configuration file for each module provides a bit of flexibility and avoids values from stepping on each other, and be configured for their exact use case.
All configuration files by default are TOML in this codebase, being powered by [KToml](https://github.com/orchestr7/ktoml).

## Does each module require its own config?

With how Yiski is designed, yes, however, a module can also opt out from having a config at all by having the module's developer not fill in the `configClass` line in their metadata.
It is however recommended that each module does have a config file that can be adjusted.

## Does every value have to be filled in?

If the value is not said to be optional by the module dev, then automatically assume that it is required.