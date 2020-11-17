package com.github.yeoj34760.spuppybot.db.user

import com.github.yeoj34760.spuppybot.db.UserDB
import net.dv8tion.jda.api.entities.User
import org.joda.time.DateTime
import java.math.BigInteger

data class UserInfo(
        val id: Long,
        val money: BigInteger,
        val receiveMoney: DateTime,
        val playlist: UserPlaylist,
        val itemList: List<UserItem>
)

fun User.info(): UserInfo = UserDB(this.idLong).invoke()