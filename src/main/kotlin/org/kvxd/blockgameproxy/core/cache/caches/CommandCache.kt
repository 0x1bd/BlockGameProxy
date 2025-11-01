package org.kvxd.blockgameproxy.core.cache.caches

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.game.command.CommandNode
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundCommandsPacket
import org.kvxd.blockgameproxy.core.cache.SyncableCache

object CommandCache : SyncableCache() {

    var nodes = arrayOf<CommandNode>()
    var firstNodeIndex = 0

    fun handlePacket(packet: ClientboundCommandsPacket) {
        reset()

        nodes = packet.nodes
        firstNodeIndex = packet.firstNodeIndex
    }

    override fun sync(session: Session) {
        session.send(
            ClientboundCommandsPacket(
                nodes,
                firstNodeIndex
            )
        )
    }

    override fun reset() {
        nodes = emptyArray()
        firstNodeIndex = 0
    }
}