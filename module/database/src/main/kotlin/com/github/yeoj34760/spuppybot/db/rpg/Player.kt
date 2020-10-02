package com.github.yeoj34760.spuppybot.db.rpg

import org.jetbrains.exposed.sql.Table

object Player : Table() {
    val id = long("id")
    val weapon = text("weapon")
    val level = integer("level")
    val monsterKill = integer("monster_kill")
}