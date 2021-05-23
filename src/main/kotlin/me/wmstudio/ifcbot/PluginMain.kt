package me.wmstudio.ifcbot

import com.github.smallshen.miraibot.plugin.BotPlugin
import io.xiaoshen.commandbuilder.command.dsl.invoke
import me.wmstudio.ifcbot.command.ifc.ifcCommands
import me.wmstudio.ifcbot.command.minecraft.bbtt.*
import me.wmstudio.ifcbot.command.minecraft.search.isUser
import me.wmstudio.ifcbot.command.other.catCommand
import me.wmstudio.ifcbot.command.play.bomb.*
import me.wmstudio.ifcbot.command.special.blacklist.blacklistCommands
import me.wmstudio.ifcbot.command.special.requestCommands
import me.wmstudio.ifcbot.command.special.specialCommands
import me.wmstudio.ifcbot.database.DB
import me.wmstudio.ifcbot.event.coinBaseEvent
import me.wmstudio.ifcbot.event.memberJoinBasic
import me.wmstudio.ifcbot.util.config.IFCConfig


lateinit var plugin: PluginMain

class PluginMain : BotPlugin("IFCBot", "1.0.0", listOf("xiaoshen"), IFCConfig("IFCBot")) {
    lateinit var dbManager: DB
    val config: IFCConfig
        get() {
            return defaultConfig!! as IFCConfig
        }
    val mainGroup = 692464440
    val orderGroup = 1140289716


    override suspend fun onPluginStart() {
        plugin = this
        dbManager = DB(this).apply { init() }
        with(bot) {
            memberJoinBasic(this@PluginMain)
            coinBaseEvent(config)
        }

        "/" {
            mainGroup {
                "help" {
                    reply { +"ifc, 2b2t, isuser, 猫图, bomb, 黑名单, kick, mute" }
                }
            }

            orderGroup {
                "help" {
                    reply { +"ifc, 2b2t, isuser, 猫图, bomb, 黑名单, 同意, 拒绝, 未处理, 查询" }
                }
            }
        }


        ifcCommands()
        bbttCommands()
        isUser()
        catCommand()
        bombCommands()
        blacklistCommands()
        requestCommands()
        specialCommands()

    }


}

