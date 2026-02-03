package one.devos.yiski.common

import java.util.regex.MatchResult

object Quotes {
    val QUOTES by lazy { Quotes::class.java.getResource("/quotes.txt")!!.readText().split("\n") }

    fun randomQuote(): String = QUOTES.random().replace("{{os}}", System.getProperty("os.name"))
}