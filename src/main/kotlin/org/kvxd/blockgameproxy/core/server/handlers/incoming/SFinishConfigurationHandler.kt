package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.game.level.notify.GameEvent
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundGameEventPacket
import org.kvxd.blockgameproxy.core.cache.caches.CommandCache
import org.kvxd.blockgameproxy.core.cache.caches.LoginCache
import org.kvxd.blockgameproxy.core.cache.caches.PlayerCache
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache
import org.kvxd.blockgameproxy.core.cache.caches.entity.EntityCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState

class SFinishConfigurationHandler : IncomingPacketHandler<ServerboundFinishConfigurationPacket> {

    override fun process(
        session: Session,
        packet: ServerboundFinishConfigurationPacket
    ): ServerboundFinishConfigurationPacket {
        session.switchState(ProtocolState.GAME)

        session.send(
            ClientboundLoginPacket(
                LoginCache.entityId,
                false,
                ChunkCache.worldNames,
                20,
                16,
                16,
                false,
                true,
                false,
                LoginCache.spawnInfo,
                false
            )
        )

        with(PlayerCache) {
            session.send(
                ClientboundPlayerPositionPacket(
                    0,
                    position,
                    positionDelta,
                    yaw,
                    pitch,
                    positionFlags
                )
            )
        }

        ChunkCache.sync(session)

        session.send(
            ClientboundGameEventPacket(
                GameEvent.LEVEL_CHUNKS_LOAD_START, null
            )
        )

        EntityCache.sync(session)

        CommandCache.sync(session)

        return packet
    }

    override val shouldForward: Boolean = false

}