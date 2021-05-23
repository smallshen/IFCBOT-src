package me.wmstudio.ifcbot.command.minecraft.search


import io.xiaoshen.commandbuilder.command.dsl.invoke
import io.xiaoshen.mojangapi.UUIDAPI
import io.xiaoshen.mojangapi.exceptions.NotAValidUser
import me.wmstudio.ifcbot.PluginMain


fun PluginMain.isUser() = "/" {
    groups {
        "isuser" {
            try {
                val resp = UUIDAPI(username = text(0)!!).conn()
                reply {

                    +"用户名: ${resp.username}"
                    newLine
                    +"UUID: ${resp.uuid}"

                }
            } catch (e: NotAValidUser) {
                reply { +"无法查询到正版id" }
            }
        }
    }
}