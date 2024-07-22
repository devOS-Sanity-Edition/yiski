package one.devos.yiski.common.utils

import one.devos.yiski.common.data.Colors
import java.awt.Color
import java.io.FileInputStream
import java.nio.file.Path

object EmbedHelpers {
    fun imagesPath(pathToImage: String): FileInputStream {
        return FileInputStream(Path.of(pathToImage).toFile())
    }
    
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
    
    
}