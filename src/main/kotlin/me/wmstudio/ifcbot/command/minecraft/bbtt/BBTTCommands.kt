package me.wmstudio.ifcbot.command.minecraft.bbtt

import io.ktor.client.*
import io.ktor.client.request.*
import io.xiaoshen.commandbuilder.command.dsl.invoke
import kotlinx.coroutines.runBlocking
import me.wmstudio.ifcbot.PluginMain

fun PluginMain.bbttCommands() = "/" {
    groups {
        "2b2t" {
            "help" {
                reply { +"stats, queue, seen, lastdeath, lastkill, tab," }
            }


            "stats" {
                reply { +runBlocking { HttpClient().get<String>("http://localhost:5000/stats/${text(0)!!}") } }
            }

            "queue" {
                reply {
                    +runBlocking {
                        HttpClient().get<String>("http://localhost:5000/prioq") + "\n" + HttpClient().get<String>("http://localhost:5000/normalq")
                    }
                }
            }
            "seen" {
                reply { +runBlocking { HttpClient().get<String>("http://localhost:5000/seen/${text(0)!!}") } }
            }

            "lastdeath" {
                reply { +runBlocking { HttpClient().get<String>("http://localhost:5000/lastdeath/${text(0)!!}") } }
            }

            "lastkill" {
                reply { +runBlocking { HttpClient().get<String>("http://localhost:5000/lastkill/${text(0)!!}") } }
            }

            "tab" {
                reply { img("https://tab.2b2t.dev/") }
            }


        }
    }
}


