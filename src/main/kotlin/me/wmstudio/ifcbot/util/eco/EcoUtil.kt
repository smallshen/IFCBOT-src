package me.wmstudio.ifcbot.util.eco

import me.wmstudio.ifcbot.database.DB
import me.wmstudio.ifcbot.database.tables.CoinStorage
import net.mamoe.mirai.contact.Member



val Member.coinStorageInstance: CoinStorage
    get() = this.coinStorageInstance


var Long.money: Float
    get() = DB.ifcTransaction {
        CoinStorage.findById(this@money)!!.coin
    }
    set(value) {
        DB.ifcTransaction {
            CoinStorage.findById(this@money)!!.coin = value
        }
    }

var Member.money: Float
    get() = this.id.money
    set(value) {
        this.id.money = value
    }

