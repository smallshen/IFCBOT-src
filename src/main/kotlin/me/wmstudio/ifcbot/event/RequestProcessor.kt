package me.wmstudio.ifcbot.event

import io.xiaoshen.mojangapi.NameChange
import net.mamoe.mirai.event.events.MemberJoinRequestEvent

object RequestProcessor {
    val requests = mutableListOf<Request>()
}

class Request(val e: MemberJoinRequestEvent, val uuid: String, val names: List<NameChange>)