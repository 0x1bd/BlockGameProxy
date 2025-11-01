package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.game.level.notify.GameEvent
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundChunkBatchFinishedPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundChunkBatchStartPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundGameEventPacket
import org.kvxd.blockgameproxy.core.cache.CacheSet
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState

class SFinishConfigurationHandler : IncomingPacketHandler<ServerboundFinishConfigurationPacket> {

    override fun process(
        session: Session,
        packet: ServerboundFinishConfigurationPacket
    ): ServerboundFinishConfigurationPacket {
        session.switchState(ProtocolState.GAME)

        session.send(ClientboundLoginPacket(
            CacheSet.Login.entityId,
            false,
            CacheSet.Chunk.worldNames,
            20,
            16,
            16,
            false,
            true,
            false,
            CacheSet.Login.spawnInfo,
            false
        ))

        with(CacheSet.Player) {
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

        with(CacheSet.Chunk) {
            session.send(ClientboundChunkBatchStartPacket())

            val packets = buildChunkSyncPackets()

            packets.forEach(session::send)

            session.send(ClientboundChunkBatchFinishedPacket(packets.size))
        }

        session.send(ClientboundGameEventPacket(
            GameEvent.LEVEL_CHUNKS_LOAD_START, null
        ))

        with(CacheSet.Entity) {
            val packets = buildSpawnSyncPackets()

            packets.forEach(session::send)
        }

        return packet
    }

    override val shouldForward: Boolean = false

}