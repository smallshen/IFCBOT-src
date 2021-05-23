package me.wmstudio.ifcbot.command.special

import io.xiaoshen.commandbuilder.command.dsl.invoke
import me.wmstudio.ifcbot.PluginMain
import me.wmstudio.ifcbot.event.RequestProcessor


fun PluginMain.requestCommands() = "/" {
    orderGroup {
        "同意" {
            RequestProcessor.requests.forEach {
                if (it.e.fromId == long(0)!!) {
                    it.e.accept()
                    RequestProcessor.requests.remove(it)
                }
            }
        }

        "拒绝" {
            RequestProcessor.requests.forEach {
                if (it.e.fromId == long(0)!!) {
                    it.e.reject(false, "请重新申请")
                    RequestProcessor.requests.remove(it)
                }
            }
        }

        "未处理" {
            reply {
                RequestProcessor.requests.forEach {
                    +"\nQQ: ${it.e.fromId} 用户名: ${it.names.last().name}"
                }
            }
        }

        "查询" {
            RequestProcessor.requests.forEach { rq ->
                if (rq.e.fromId == long(0)!!) {
                    reply {
                        var m = """
                            新入群申请 !
                            QQ: ${rq.e.fromId}
                            昵称: ${rq.e.fromNick}
                            UUID: ${rq.uuid}
                            最近名字: ${rq.names.last().name}
                            历史名字:
                        """.trimIndent()

                        rq.names.forEach {
                            m += "\n${it.name}"
                        }
                        +m
                    }
                }
            }
        }
    }
}