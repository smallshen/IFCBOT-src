package me.wmstudio.ifcbot.database.tables


import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object CoinStorageTable : LongIdTable(columnName = "qq") {
    var coin = float("coin").default(0F)
}

class CoinStorage(qq: EntityID<Long>) : LongEntity(qq) {
    companion object : LongEntityClass<CoinStorage>(CoinStorageTable)

    val qq by CoinStorageTable.id
    var coin by CoinStorageTable.coin
}
