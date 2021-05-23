package me.wmstudio.ifcbot.command.special


import com.github.smallshen.miraibot.script.reloadScripts
import io.xiaoshen.commandbuilder.command.admin
import io.xiaoshen.commandbuilder.command.dsl.invoke
import io.xiaoshen.commandbuilder.command.member
import io.xiaoshen.commandbuilder.member
import me.wmstudio.ifcbot.PluginMain
import net.mamoe.mirai.message.nextMessage
import java.io.File


fun PluginMain.specialCommands() = "/" {
    mainGroup {
        "kick" {
            admin {
                member(0)!!.apply { kick() }
                reply { +"已踢出" }
            }
        }

        "mute" {
            admin {
                member(0)!!.apply { mute(int(1)!! * 60) }
            }

            member {
                e.sender.apply { mute(int(1)!! * 60) }
            }
        }

    }

    orderGroup {
        "scripts" {

            "reload" {
                reloadScripts()
                reply {
                    +"重载完成"
                }
            }

            "list" {
                reply {
                    +File("scripts").listFiles()!!.joinToString(", ")
                }
            }

            "add" {
                val file = File("scripts/${text(0)!!}.kts").apply { createNewFile() }
                reply {
                    +"已创建脚本 ${text(0)!!}.kts"
                    newLine
                    +"请输入脚本内容"
                }

                val scriptsContent = e.nextMessage().contentToString()
                file.writeText(scriptsContent)
                reply {
                    +"写入成功"
                }
            }

        }
    }
}