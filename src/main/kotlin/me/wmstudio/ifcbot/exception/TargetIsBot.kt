package me.wmstudio.ifcbot.exception

class TargetIsBot(message: String) : Exception(message) {
    constructor() : this("You can't interact with bot")
}