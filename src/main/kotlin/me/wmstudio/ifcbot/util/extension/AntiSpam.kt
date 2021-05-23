package me.wmstudio.ifcbot.util.extension

import net.mamoe.mirai.contact.Member

val Member.messages: MutableList<String>
    get() {
        if (spamCount[this] == null) {
            spamCount[this] = mutableListOf()
        }
        return spamCount[this]!!
    }

val spamCount = mutableMapOf<Member, MutableList<String>>()