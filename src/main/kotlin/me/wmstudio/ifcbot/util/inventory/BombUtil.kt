package me.wmstudio.ifcbot.util.inventory

import me.wmstudio.ifcbot.database.DB
import me.wmstudio.ifcbot.database.tables.UserInv
import net.mamoe.mirai.contact.Member


val Long.inventoryInstance: UserInv
    get() {
        var u: UserInv? = null
        DB.ifcTransaction {
            u = UserInv.findById(this@inventoryInstance)
        }

        return u!!
    }

val Member.inventoryInstance: UserInv
    get() {
        return this.id.inventoryInstance
    }


var Long.bomb: Int
    get() {
        val p = DB.ifcTransaction {
            UserInv.findById(this@bomb)!!
        }

        return p.bomb
    }
    set(value) {
        DB.ifcTransaction {
            UserInv.findById(this@bomb)!!.bomb = value
        }
    }


var Member.bomb: Int
    get() = this.id.bomb
    set(value) {
        this.id.bomb = value
    }
