package one.devos.yiski.common.utils

object RandomTextList {
    fun sillyFooters(): String {
        val footers: List<String> = listOf( "hihi", "uwu", "owo", "wazbewwy pwi fwour?? uwu?", "-w-", "hwody fwen!", "fern!- i mean frewn!", "meep", "hihi again!", "hai!")
        return footers.random()
    }

    fun sillyFakeBanFooters(): String {
        val footers: List<String> = listOf("lmao", "uwu", "owo", "wheeze", "get bamboozled")
        return footers.random()
    }
}