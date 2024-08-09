package one.devos.yiski.common.utils

import java.io.InputStream

object PathsHelper {
    enum class FileType(val s: String) {
        VIDEO("assets/videos"),
        IMAGE("assets/images")
    }

    fun filePath(pathToFile: String): InputStream {
        return this::class.java.classLoader.getResourceAsStream(pathToFile) ?: error("Could not get file from given path")
    }

    object filePath {
        fun file(fileName: String): String {
            return fileName
        }

        fun path(filetype: Enum<FileType>, file: String): InputStream {
            return filePath("${filetype.toString()}/${file.toString()}")
        }
    }
}