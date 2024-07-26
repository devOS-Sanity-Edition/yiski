package one.devos.yiski.common.annotations

@RequiresOptIn(level = RequiresOptIn.Level.ERROR, message = "This API is experimental, and still a work in progress with the Yiski Module Loader. This annotation will be removed once the loader is more stable, but for now... Here be dragons.")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
//@Deprecated("The loader is now stable enough, please remove this annotation as it's no longer needed and will be removed in a future release.") // leaving this here in the future, also change the ERROR type to WARNING once it's complete too
annotation class YiskiModule
