package me.wmstudio.ifcbot.event


import me.wmstudio.ifcbot.plugin
import me.wmstudio.ifcbot.util.config.IFCConfig
import me.wmstudio.ifcbot.util.eco.money
import me.wmstudio.ifcbot.util.extension.messages
import me.wmstudio.ifcbot.util.process.removeUnnecessary
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.isAdministrator
import net.mamoe.mirai.contact.isOwner
import net.mamoe.mirai.event.Listener
import net.mamoe.mirai.event.subscribeAlways
import net.mamoe.mirai.message.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.message.data.PlainText

fun Bot.coinBaseEvent(config: IFCConfig) {
    this.subscribeAlways<GroupMessageEvent>(priority = Listener.EventPriority.HIGHEST) {
        if (this.group.id != config.mainGroup) {
            return@subscribeAlways
        }
        if (this.sender.isOwner() || this.sender.isAdministrator()) {
            return@subscribeAlways
        }

        sender.messages += this.message.contentToString()
        if (sender.messages.size >= 6) {
            if (sender.messages.distinct().size == 1) {
                this.sender.kick("刷屏检测")
                plugin.logger.log("${this.sender.id} 检测到刷屏, 踢出")
            }
        }

    }

    this.subscribeAlways<GroupMessageEvent>(priority = Listener.EventPriority.MONITOR) {
        if (this.group.id == config.mainGroup) {
            val messageRebuild = MessageChainBuilder()
            for (m in this.message) {
                if (m != this.message[0]) {
                    if (m is PlainText) {
                        messageRebuild.add(m)
                    }
                }
            }

            if (messageRebuild.asMessageChain().contentToString().removeUnnecessary().length > 5) {
                this.sender.id.money += 0.2F
            }
        }
    }
}

