package one.devos.yiski.common.utils

import kotlin.time.Duration

fun Duration.toTimeString() : String {
    return "${zeroPad(this.inWholeMinutes - (this.inWholeHours * 60))}:${zeroPad(this.inWholeSeconds - (this.inWholeMinutes * 60))}"
}

private fun zeroPad(num: Long): String {
    return if (num <= 9)
        "0$num"
    else
        "$num"
}