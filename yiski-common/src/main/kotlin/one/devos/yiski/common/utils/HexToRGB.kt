package one.devos.yiski.common.utils

fun HexToRGB(hex: String): Triple<Int, Int, Int>? {
    // Check if the hex string is valid syntax
    if (!hex.matches(Regex("^#?([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")))
        return null

    // Remove '#' symbol if present
    val unpoundedHex = if (hex[0] == '#') hex.substring(1) else hex

    // Expand short hex format to full format if necessary
    val fullSizeHex = if (unpoundedHex.length == 3) {
        unpoundedHex.map { it.toString() + it }.joinToString("")
    } else {
        unpoundedHex
    }

    // Convert hex to RGB values
    val red = fullSizeHex.substring(0, 2).toInt(16)
    val green = fullSizeHex.substring(2, 4).toInt(16)
    val blue = fullSizeHex.substring(4, 6).toInt(16)

    return Triple(red, green, blue)
}
