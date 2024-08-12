package one.devos.yiski3.utils

/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Nicholas Sylke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

// converted to kotlin by asojidev
// orig: https://github.com/nsylke/Zalgo4J/blob/master/src/main/java/me/nsylke/zalgo4j/Zalgo4J.java

import kotlin.random.Random

object Zalgo {
    private val ZALGO_UP = charArrayOf(
        '̍', '̎', '̄', '̅',
        '̿', '̑', '̆', '̐',
        '͒', '͗', '͑', '̇',
        '̈', '̊', '͂', '̓',
        '̈́', '͊', '͋', '͌',
        '̃', '̂', '̌', '͐',
        '̀', '́', '̋', '̏',
        '̒', '̓', '̔', '̽',
        '̉', 'ͣ', 'ͤ', 'ͥ',
        'ͦ', 'ͧ', 'ͨ', 'ͩ',
        'ͪ', 'ͫ', 'ͬ', 'ͭ',
        'ͮ', 'ͯ', '̾', '͛',
        '͆', '̚'
    )

    private val ZALGO_MIDDLE = charArrayOf(
        '̕', '̛', '̀', '́',
        '͘', '̡', '̢', '̧',
        '̨', '̴', '̵', '̶',
        '͏', '͜', '͝', '͞',
        '͟', '͠', '͢', '̸',
        '̷', '͡', '҉'
    )

    private val ZALGO_DOWN = charArrayOf(
        '̖', '̗', '̘', '̙',
        '̜', '̝', '̞', '̟',
        '̠', '̤', '̥', '̦',
        '̩', '̪', '̫', '̬',
        '̭', '̮', '̯', '̰',
        '̱', '̲', '̳', '̹',
        '̺', '̻', '̼', 'ͅ',
        '͇', '͈', '͉', '͍',
        '͎', '͓', '͔', '͕',
        '͖', '͙', '͚', '̣'
    )

    fun zalgolize(text: String): String {
        val builder = StringBuilder()
        val random: Random = Random

        for (i in 0 until text.length) {
            var temp = text[i].toString()

            for (j in 0 until (random.nextInt(8) / 2 + 1)) {
                temp += ZALGO_UP[random.nextInt(ZALGO_UP.size)]
            }

            for (j in 0 until (random.nextInt(6) / 2)) {
                temp += ZALGO_MIDDLE[random.nextInt(ZALGO_MIDDLE.size)]
            }

            for (j in 0 until (random.nextInt(8) / 2 + 1)) {
                temp += ZALGO_DOWN[random.nextInt(ZALGO_DOWN.size)]
            }

            builder.append(temp)
        }

        return builder.toString()
    }
}