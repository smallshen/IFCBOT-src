package me.wmstudio.ifcbot.command.ifc


import com.github.smallshen.miraibot.BotConsole
import io.xiaoshen.commandbuilder.command.dsl.invoke
import io.xiaoshen.commandbuilder.member
import me.wmstudio.ifcbot.PluginMain
import me.wmstudio.ifcbot.util.eco.money

fun PluginMain.ifcCommands() {
    "/" {
        mainGroup {
            "ifc" {
                "pay" {
                    val target = member(0)!!
                    val amount = text(1)?.toFloatOrNull()
                    when {
                        target.id == bot.id -> reply { +"支付失败, 你无法与机器人产生经济上的互动" }

                        amount == null -> reply { +"非法金额" }

                        amount <= 0 -> reply { +"非法金额" }

                        e.sender.id.money < amount -> reply { +"你没有足够的金钱" }

                        else -> {
                            e.sender.money -= amount
                            target.money += amount
                            reply {
                                at(e.sender)
                                plain(" 成功的向 ")
                                at(target)
                                plain(" 支付了 $amount IFC")
                            }
                        }
                    }
                }

                "remain" {
                    reply {
                        at(e.sender)
                        +" 当前剩余IFC: ${e.sender.money}"
                    }
                }
            }
        }

        orderGroup {
            "ifc" {
                "set" {
                    val target = long(0)!!
                    val amount = text(1)!!.toFloat()
                    target.money = amount
                    reply {
                        plain("${e.senderName} 将 $target 的 IFC 金额设置为 $amount")
                    }
                }
            }
        }
    }
}