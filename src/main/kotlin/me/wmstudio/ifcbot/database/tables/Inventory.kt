package me.wmstudio.ifcbot.database.tables

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object Inventory : LongIdTable(columnName = "qq") {
    var bomb = integer("bomb").default(0)
}

class UserInv(qq: EntityID<Long>) : LongEntity(qq) {
    companion object : LongEntityClass<UserInv>(Inventory)

    val qq by Inventory.id
    var bomb by Inventory.bomb
}