package org.kvxd.blockgameproxy.core.cache.caches

import org.geysermc.mcprotocollib.protocol.data.game.command.CommandNode

class CommandCache(
    var nodes: Array<CommandNode> = arrayOf(),
    var firstNodeIdx: Int = 0
)