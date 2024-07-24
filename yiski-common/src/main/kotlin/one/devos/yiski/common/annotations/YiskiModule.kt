package one.devos.yiski.common.annotations

@RequiresOptIn(level = RequiresOptIn.Level.ERROR, message = "This API is experimental, and still a work in progress with the Yiski Module Loader. This annotation will be removed once the loader is more stable, but for now... Here be dragons.")
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class YiskiModule
