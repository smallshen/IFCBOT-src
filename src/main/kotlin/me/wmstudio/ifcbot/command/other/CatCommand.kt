package me.wmstudio.ifcbot.command.other

import io.xiaoshen.commandbuilder.command.dsl.invoke
import me.wmstudio.ifcbot.PluginMain

fun PluginMain.catCommand() = "/" {
    "猫图" {
        reply {
            img("https://thiscatdoesnotexist.com/")
        }
    }
}