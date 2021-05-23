package me.wmstudio.ifcbot.util.db

import me.wmstudio.ifcbot.database.DB
import me.wmstudio.ifcbot.database.tables.BlackListTable
import me.wmstudio.ifcbot.database.tables.Blacklist
import net.mamoe.mirai.contact.Member
import org.jetbrains.exposed.sql.selectAll

object BlacklistUtil {
    fun getBlacklists(): List<Long> {
        return DB.ifcTransaction {
            BlackListTable.selectAll().map { it[BlackListTable.id] }.map { it.value }
        }
    }
}

var Long.inBlacklist: Boolean
    get() {
        return DB.ifcTransaction {
            Blacklist.findById(this@inBlacklist)
        } != null
    }
    set(value) {
        when (value) {
            true -> {
                if (!inBlacklist) {
                    Blacklist.new(this) {}
                }
            }

            false -> {
                DB.ifcTransaction {
                    Blacklist.findById(this@inBlacklist)?.delete()
                }
            }
        }
    }

var Member.inBlacklist: Boolean
    get() {
        return this.id.inBlacklist
    }
    set(value) {
        this.id.inBlacklist = value
    }