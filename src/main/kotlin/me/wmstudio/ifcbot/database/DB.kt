package me.wmstudio.ifcbot.database


import me.wmstudio.ifcbot.PluginMain
import me.wmstudio.ifcbot.database.tables.*
import me.wmstudio.ifcbot.database.tables.shop.ItemTable
import me.wmstudio.ifcbot.util.config.IFCConfig
import net.mamoe.mirai.contact.Group
import org.hydev.logger.coloring.GradientPresets
import org.hydev.logger.format.AnsiColor
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Database.Companion.connect
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.math.roundToInt

class DB(private val botPlugin: PluginMain) {
    companion object {
        lateinit var db: Database

        fun <T> ifcTransaction(statement: Transaction.() -> T): T = transaction(db, statement)
    }

    fun init() {
        connect()
        createDatabase()
        updateDatabase(botPlugin.config)
    }

    fun connect() {
        with(botPlugin) {
            logger.timing.reset()
            logger.fancy.gradient("连接数据库中。。。。", GradientPresets.BPR)

            db = connect(
                "jdbc:sqlite:${botPlugin.pluginDir.absolutePath}/${botPlugin.config.mainGroup}.db",
                "org.sqlite.JDBC"
            )

            logger.log(
                String.format(
                    "%s连接数据库成功! %s(%s ms)",
                    AnsiColor.GREEN,
                    AnsiColor.YELLOW,
                    (logger.timing.elapsed * 100.0).roundToInt() / 100.0
                )
            )
            logger.timing.reset()
            logger.log("检测数据库中。。。")
        }
    }

    fun createDatabase() = ifcTransaction {
        SchemaUtils.create(CoinStorageTable, Inventory, BlackListTable, ItemTable)
    }

    fun updateDatabase(ifcConfig: IFCConfig) {
        updateInventory(botPlugin.bot.getGroup(ifcConfig.mainGroup))
        updateCoinStorage(botPlugin.bot.getGroup(ifcConfig.mainGroup))
    }

    fun updateInventory(group: Group) = ifcTransaction {
        group.members.forEach {
            UserInv.findById(it.id) ?: UserInv.new(it.id) {}
        }
    }

    fun updateCoinStorage(group: Group) = ifcTransaction {
        group.members.forEach {
            CoinStorage.findById(it.id) ?: CoinStorage.new(it.id) {}
        }
    }

}
