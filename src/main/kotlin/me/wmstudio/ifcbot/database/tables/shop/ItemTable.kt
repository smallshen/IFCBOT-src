package me.wmstudio.ifcbot.database.tables.shop

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object ItemTable : IntIdTable("shops") {
    val name = text("item_name")
    val price = float("item_price")
    val description = text("item_description")
    val amount = integer("item_remain")
    val infiniteAmount = bool("has_amount_restriction").default(false)
}

class ShopItem(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ShopItem>(ItemTable)

    val name by ItemTable.name
    var price by ItemTable.price
    var description by ItemTable.description
    var remain by ItemTable.amount
    var infiniteAmount by ItemTable.infiniteAmount

    fun canBuy(amount: Int): Boolean {
        if (infiniteAmount) {
            return true
        } else {
            return amount <= remain
        }
    }
}