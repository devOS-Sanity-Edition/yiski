package one.devos.yiski.common.utils

import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.data.Colors
import java.awt.Color
import java.io.File
import java.io.InputStream

object EmbedHelpers {
    fun successColor(successRed: Int = Colors.SUCCESS.r, successGreen: Int = Colors.SUCCESS.g, successBlue: Int = Colors.SUCCESS.b): Int {
        return Color(successRed, successGreen, successBlue).rgb
    }

    fun warnColor(warnRed: Int = Colors.WARNING.r, warnGreen: Int = Colors.WARNING.g, warnBlue: Int = Colors.WARNING.b): Int {
        return Color(warnRed, warnGreen, warnBlue).rgb
    }

    fun failColor(failRed: Int = Colors.FAIL.r, failGreen: Int = Colors.FAIL.g, failBlue: Int = Colors.FAIL.b): Int {
        return Color(failRed, failGreen, failBlue).rgb
    }

    fun infoColor(infoRed: Int = Colors.INFO.r, infoGreen: Int = Colors.INFO.g, infoBlue: Int = Colors.INFO.b): Int {
        return Color(infoRed, infoGreen, infoBlue).rgb
    }

    fun moderationColor(modRed: Int = Colors.MODERATION.r, modGreen: Int = Colors.MODERATION.g, modBlue: Int = Colors.MODERATION.b): Int {
        return Color(modRed, modGreen, modBlue).rgb
    }

    fun imageUpload(file: String): FileUpload {
        return FileUpload.fromData(PathsHelper.filePath.path(PathsHelper.FileType.IMAGE, file), PathsHelper.filePath.file(file))
    }

    fun videoUpload(file: String): FileUpload {
        return FileUpload.fromData(PathsHelper.filePath.path(PathsHelper.FileType.VIDEO, file), PathsHelper.filePath.file(file))
    }
}