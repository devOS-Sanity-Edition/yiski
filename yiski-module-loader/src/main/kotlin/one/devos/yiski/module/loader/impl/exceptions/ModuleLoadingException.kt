package one.devos.yiski.module.loader.exceptions

class ModuleLoadingException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)
