package one.devos.yiski.common

object Quotes {
    val QUOTES by lazy { Quotes::class.java.getResource("/quotes.txt")!!.readText().split("\n") }

    fun randomQuote(): String = QUOTES.random().replace("{{os}}", System.getProperty("os.name"))
}