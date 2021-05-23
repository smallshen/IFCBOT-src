package me.wmstudio.ifcbot.util.process

import com.vdurmont.emoji.EmojiParser
import java.net.URL
import java.nio.charset.Charset

fun String.removeUnnecessary(): String {
    return this.replace(" ").replace(Regex("\\s*")).replace(Regex(" +"))
        .replace(Regex("\\p{P}")).replace("awa").replace("qwq").replace("minecraft")
        .replace("2b2t").replace("mc").replace(".org").replace("china").replace(".xin")
        .replace(".cn").replace("无限原力")
}

fun String.isAllChinese(): Boolean {
    return (this.matches(Regex("[\\u4e00-\\u9fa5]+")))
}

fun String.replace(oldChar: String): String {
    return this.replace(oldChar, "")
}

fun String.replace(regex: Regex): String {
    return this.replace(regex, "")
}


fun String.removeEmoji(): String {
    return EmojiParser.removeAllEmojis(this)
}

fun String.removeChineseChar(): String {
    return (this.replace(Regex("[\\u4e00-\\u9fa5]+")))
}


fun String.removeCoinNotAllowedChar(): String {
    return this.toLowerCase()
        .replace("-", "").replace("e", "")
}

fun String.webPage(): String {
    return URL(this).readText(charset = Charset.forName("UTF-8"))
}