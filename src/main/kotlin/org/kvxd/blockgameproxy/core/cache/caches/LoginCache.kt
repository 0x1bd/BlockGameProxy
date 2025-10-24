package org.kvxd.blockgameproxy.core.cache.caches

import net.kyori.adventure.key.Key
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.GameMode
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.PlayerSpawnInfo

class LoginCache(
    var entityId: Int = 1,
    var worldNames: Array<Key> = arrayOf(Key.key("overworld"), Key.key("the_nether"), Key.key("the_end")),
    var spawnInfo: PlayerSpawnInfo = PlayerSpawnInfo(
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
    ),
    var enforceSecureChat: Boolean = false
)