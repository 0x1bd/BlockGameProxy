package org.kvxd.blockgameproxy.core.cache.caches

import net.kyori.adventure.key.Key
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.GameMode
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.PlayerSpawnInfo
import org.kvxd.blockgameproxy.core.cache.Cache

object LoginCache : Cache() {

    var entityId by resettable(0)
    var spawnInfo by resettable(
        PlayerSpawnInfo(
            0,
            Key.key("overworld"),
            4474032083008373353,
            GameMode.SURVIVAL,
            null,
            false,
            false,
            null,
            0,
            -63
        )
    )

}