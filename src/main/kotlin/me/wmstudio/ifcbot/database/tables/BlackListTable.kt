package me.wmstudio.ifcbot.database.tables

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object BlackListTable : LongIdTable(name = "blacklist", columnName = "qq")

class Blacklist(qq : EntityID<Long>): LongEntity(qq) {
    companion object : LongEntityClass<Blacklist>(BlackListTable)
}