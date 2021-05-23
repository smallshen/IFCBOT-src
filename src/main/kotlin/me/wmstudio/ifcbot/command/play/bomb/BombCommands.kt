package me.wmstudio.ifcbot.command.play.bomb

import io.xiaoshen.commandbuilder.command.dsl.invoke
import io.xiaoshen.commandbuilder.member
import me.wmstudio.ifcbot.PluginMain
import me.wmstudio.ifcbot.util.eco.money
import me.wmstudio.ifcbot.util.inventory.bomb
import me.wmstudio.ifcbot.util.process.removeCoinNotAllowedChar
import java.util.*

fun PluginMain.bombCommands() =
    "/" {
        mainGroup {
            "bomb" {

                "扔炸弹" {
                    val target = member(0)

                    when {

                        target == null -> reply { +"格式错误" }
                        e.sender.bomb <= 0 -> reply { +"你没有足够的炸弹" }

                        target.id == bot.id -> reply { +"群里那么多人不炸非要来搞我, 有病" }

                        else -> {
                            val isHit = random(1, 100) < 50
                            e.sender.bomb -= 1
                            target.apply {
                                if (isHit) {
                                    val duration = random(300, 1000)
                                    mute(duration)
                                    reply {
                                        at(target)
                                        plain(" 被 ")
                                        at(e.sender)

                                        plain(" 使用炸弹击中")
                                        newLine()

                                        +"你好惨唉，也许你们天生不是一对，给你禁言${duration}秒\n"
                                        plain("你可以使用 /扔炸弹 来反击哦")
                                    }
                                } else {
                                    reply {
                                        at(e.sender)
                                        +" 很抱歉，你没有击中他唉，我看你印堂发黑，可能你今天运气不好"
                                    }
                                }
                            }
                        }
                    }
                }

                "剩余炸弹" {
                    reply { +"当前 炸弹 数量: ${e.sender.bomb}" }
                }

                "购买炸弹" {
                    val price = 10f
                    val amount = text(0)?.removeCoinNotAllowedChar()?.toIntOrNull()
                    when {
                        amount == null -> reply { +"非法数量" }

                        amount < 1 -> reply { +"非法数量" }

                        else -> {
                            val money = price * amount
                            if (e.sender.id.money < money) {
                                reply { +"你没有足够的金钱\n总共需要 $money 而你只有 ${e.sender.id.money}" }
                            } else {
                                e.sender.apply {
                                    this.id.money -= money
                                    bomb += amount
                                }

                                reply {
                                    at(e.sender)
                                    newLine
                                    +"成功购买 炸弹 X $amount"
                                    newLine
                                    +"当前剩余炸弹: ${e.sender.bomb}"
                                }
                            }
                        }

                    }
                }
            }
        }

        orderGroup {
            "bomb" {

                "set" {
                    val target = long(0)!!
                    val amount = int(1)!!
                    target.bomb = amount
                    reply {
                        plain("${e.sender.id} 将 $target 的 炸弹 数量设置为 $amount")
                    }
                }
            }
        }
    }


fun random(min: Int, max: Int): Int {
    val random = Random()
    return random.nextInt(max) % (max - min + 1) + min
}