package me.wmstudio.ifcbot.event

import io.xiaoshen.mojangapi.NameHistoryAPI
import io.xiaoshen.mojangapi.exceptions.NotAValidUser
import kotlinx.coroutines.delay
import me.wmstudio.ifcbot.PluginMain
import me.wmstudio.ifcbot.util.db.inBlacklist
import me.wmstudio.ifcbot.util.process.replace
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.events.MemberJoinEvent
import net.mamoe.mirai.event.events.MemberJoinRequestEvent
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChainBuilder
import org.hydev.logger.HyLogger

/**
 * @author xiaoshen
 * 这段代码建议不要看，看了你会打人，因为写的很烂，懒（bushi
 */

fun Bot.memberJoinBasic(plugin: PluginMain) {
    this.subscribeAlways<MemberJoinEvent> {
        if (this.member.id.inBlacklist) {
            this.member.kick("黑名单")
            return@subscribeAlways
        } else if (this.group.id == plugin.config.mainGroup) {
            delay(1000)

            plugin.dbManager.updateDatabase(plugin.config)


            this.member.mute(60 * 15)


            delay(1000)
            this.member.sendMessage("刚加入QQ群聊你将会被禁言15分钟, 这是一个冷静期")
            delay(1500)
            this.member.sendMessage("在这个冷静期, 请您务必查看群文件里面的总章程，群公告")
            delay(1500)
            this.member.sendMessage("请修不要问一些弱智问题，大部分规则写在公告里面")
            delay(2000)
            this.member.sendMessage("群里不能有任何人民币交易，如果发现有人民币交易，请及时告知管理员")
        }
    }

    this.subscribeAlways<MemberJoinRequestEvent> {
        HyLogger("加群申请").log("<${this.group.id}> [${this.fromId}]${this.message.replace("\n", "")}")

        if (this.group.id == plugin.config.mainGroup) {
            if (this.fromId.inBlacklist) {
                this.reject(false, "滚")
                return@subscribeAlways
            } else {
                val answer = this.message.split("\n答案：", limit = 2)[1].replace("\n").replace("-")
                if (answer.matches("[a-zA-Z0-9]{32}".toRegex())) {
                    try {
                        val r = NameHistoryAPI(uuid = answer).conn()

                        val request = Request(this, uuid = answer, names = r)
                        RequestProcessor.requests.forEach {
                            if (it.e.fromId == this.fromId) {
                                RequestProcessor.requests.remove(it)
                            }
                        }

                        RequestProcessor.requests += request

                        var m = """
                            新入群申请 !
                            QQ: ${this.fromId}
                            昵称: ${this.fromNick}
                            UUID: $answer
                            历史名字:
                            
                        """.trimIndent()

                        r.forEach {
                            m += "${it.name}  "
                        }

                        plugin.bot.getGroup(plugin.config.orderGroup).sendMessage(m)


                    } catch (e: NotAValidUser) {
                        this.reject(false, "没有检测到你的正版账号")
                    }
                } else {
                    this.reject(false, "没有检测到你的正版账号")
                }
            }

        }
    }
}



