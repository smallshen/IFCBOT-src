package me.wmstudio.ifcbot.util.config

import com.github.smallshen.miraibot.plugin.PluginConfig

class IFCConfig(val dir: String) : PluginConfig("config.yml") {


    override fun onInit() {
        this.set("username", "root")
        this.set("password", "rootPassword")
        this.set("Main Group", 123)
        this.set("Order Group", 123)
        this.set("bot.account", "youremail.com")
        this.set("bot.password", "yourpassword")
        save()
    }

    val JDBCUrl get() = this.getString("jdbc")


    val DBUsername get() = this.getString("username")


    val DBPassword get() = this.getString("password")


    val mainGroup get() = this.getLong("Main Group")

    val orderGroup get() = getLong("Order Group")


}