# Module Loader

<primary-label ref="author-deftu"/>

## Initial setup

For the module loader to know where and how to discover modules, you need to register [discoverers](#discovery), and then call the `ModuleLoader#discover` function. This function will give you a set of module metadata objects which contain all relevant information needed to set up a module and have it interact with our main runner.

## Entrypoints

The module loader uses an entrypoint system to allow modules to have their own logic run elsewhere (including inside other modules!). Using it is as simple as loading the module into the current classpath then passing a reference to their class into `ModuleLoader#loadEntrypoint`. Not only does this create an instance of the class given to it, but it allows the caller to pass their own constructor arguments to it. This **IS** validated at creation time to ensure that there are no future issues.

## Loading your own entrypoints

To create your own entrypoint instance, you'll need a reference to a class implements it. For example,

```kotlin
package com.example

import one.devos.yiski.module.loader.api.entrypoints.Entrypoint

interface MyEntrypoint : Entrypoint {

    fun meow()

}

class MyEntrypointImpl : MyEntrypoint {

    override fun meow() {
        println("Meow")
    }

}
```


```kotlin
package com.example

import one.devos.yiski.module.loader.impl.ModuleLoader

val moduleLoader: ModuleLoader = ...
val clz: Class<*> = Class.forName("com.example.MyEntrypointImpl")
val entrypoint = moduleLoader.loadEntrypoint(clz) as MyEntrypoint
```

## Obtaining another module's metadata

Each module has its own ID, which is unique to that module. This ID can be used to obtain other modules' metadata (and thus, their entrypoints). This can be achieved using `ModuleLoader#getMetadataFor`.

Alternatively, if you need the metadata of all currently loaded modules, `ModuleLoader#getModules` is exposed.

## Discovery

Modules are discovered via implementations of `ModuleDiscoverer`, which provides a set of paths (either physical or virtual) in which modules _will_ be stored. Potential duplicate modules will overwrite one another, the last found duplicate taking precedence.

> At least one discoverer is required for any modules to be discovered! Without a discoverer registered, no modules will be loaded.
{style="note"}

### Default discoverers

`ClasspathModuleDiscoverer`
: Discovers any modules currently loaded. It's best practice to always register this loader first to avoid unnecessary duplicates.

`JarModuleDiscoverer`
: Loads all modules within JAR files in it's given path. For example, if given the `modules` relative path, it would iterate through every `.jar` file inside that path in an attempt to identify a module. (f.ex, `/home/example/yiski/modules/module1.jar`, `/home/example/yiski/modules/module2.jar`, etc)

### Discovery pipeline

- First, all module discovers are called (and forcibly made distinct to avoid duplicates)
- Then, we begin iterating through each path returned by the module discoverers
  - If the path is for a file,
    - We load the file as a JAR file, then searching it's root for a `yiski.metadata.toml` ([see the module metadata](Modules-Metadata.md))
    - If we find one, we read its contents into our metadata list and load the JAR file into our current classpath
  - Otherwise, if the path points to a directory,
    - We simply look for a `yiski.metadata.toml` inside the directory, and read its contents into our metadata list
- Finally, we read and validate all the sources in our metadata list, creating a list of all of our newly parsed `ModuleMetadata` objects
- These are then added to our loaded modules list and returned to the call of the `ModuleLoader#discover` function

<seealso style="cards">
    <category ref="related-modules-data" >
        <a href="Modules-Metadata.md" summary="Take a look at how the Modules Metadata is done and its relevancy."></a>
    </category>
</seealso>