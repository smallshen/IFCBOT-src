package me.wmstudio.ifcbot.command.special.blacklist

import io.xiaoshen.commandbuilder.command.dsl.invoke
import kotlinx.coroutines.runBlocking
import me.wmstudio.ifcbot.PluginMain
import me.wmstudio.ifcbot.database.DB
import me.wmstudio.ifcbot.util.db.BlacklistUtil
import me.wmstudio.ifcbot.util.db.inBlacklist

fun PluginMain.blacklistCommands() = "/" {
    mainGroup {
        "黑名单" {
            "list" {
                reply { +"黑名单列表: ${BlacklistUtil.getBlacklists()}" }
            }
        }
    }


    orderGroup {
        "黑名单" {

            "list" {
                reply { +"黑名单列表: ${BlacklistUtil.getBlacklists()}" }
            }

            "add" {
                DB.ifcTransaction {
                    runBlocking {
                        when (val m = long(0)) {
                            null -> reply { +"格式错误" }

                            else -> {
                                val q = this@blacklistCommands.bot.getGroup(this@blacklistCommands.config.mainGroup)
                                    .getOrNull(m)
                                m.inBlacklist = true

                                q?.apply {
                                    kick()
                                }

                                reply {
                                    +"添加 $m 到黑名单"
                                }
                            }
                        }
                    }
                }
            }


            "del" {
                when (val m = long(0)) {
                    null -> reply { +"格式错误" }

                    else -> {
                        val q =
                            this@blacklistCommands.bot.getGroup(this@blacklistCommands.config.mainGroup).getOrNull(m)
                        m.inBlacklist = false
                        q?.apply {
                            kick()
                        }

                        reply { +"从黑名单删除 $m" }
                    }
                }
            }
        }
    }
}