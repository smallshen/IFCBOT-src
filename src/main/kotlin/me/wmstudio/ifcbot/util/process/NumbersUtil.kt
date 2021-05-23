package me.wmstudio.ifcbot.util.process

import me.wmstudio.ifcbot.plugin
import net.mamoe.mirai.contact.Group

val Long.asGroup: Group
    get() = plugin.bot.getGroup(this)