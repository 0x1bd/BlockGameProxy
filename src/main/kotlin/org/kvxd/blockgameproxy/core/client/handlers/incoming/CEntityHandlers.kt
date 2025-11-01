package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundEntityPositionSyncPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityPosPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityPosRotPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityRotPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundTeleportEntityPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.spawn.ClientboundAddEntityPacket
import org.kvxd.blockgameproxy.core.cache.CacheSet
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistry

object CEntityHandlers {

    fun PacketHandlerRegistry.registerEntityHandlers() {
        registerIncoming(CAddEntityHandler())
        registerIncoming(CSyncEntityPositionHandler())
        registerIncoming(CTeleportEntityHandler())
        registerIncoming(CMovePosHandler())
        registerIncoming(CMoveRotHandler())
        registerIncoming(CMovePosRotHandler())
    }

}

class CAddEntityHandler : IncomingPacketHandler<ClientboundAddEntityPacket> {

    override fun process(
        session: Session,
        packet: ClientboundAddEntityPacket
    ): ClientboundAddEntityPacket {
        CacheSet.Entity.handleSpawnPacket(packet)

        return packet
    }
}

class CSyncEntityPositionHandler: IncomingPacketHandler<ClientboundEntityPositionSyncPacket> {

    override fun process(
        session: Session,
        packet: ClientboundEntityPositionSyncPacket
    ): ClientboundEntityPositionSyncPacket {
        CacheSet.Entity.handleSyncPacket(packet)

        return packet
    }

}

class CTeleportEntityHandler: IncomingPacketHandler<ClientboundTeleportEntityPacket> {

    override fun process(
        session: Session,
        packet: ClientboundTeleportEntityPacket
    ): ClientboundTeleportEntityPacket {
        CacheSet.Entity.handleTeleportPacket(packet)

        return packet
    }

}

class CMovePosHandler : IncomingPacketHandler<ClientboundMoveEntityPosPacket> {

    override fun process(session: Session, packet: ClientboundMoveEntityPosPacket): ClientboundMoveEntityPosPacket {
        CacheSet.Entity.handleMovePos(packet)

        return packet
    }

}

class CMoveRotHandler : IncomingPacketHandler<ClientboundMoveEntityRotPacket> {

    override fun process(session: Session, packet: ClientboundMoveEntityRotPacket): ClientboundMoveEntityRotPacket {
        CacheSet.Entity.handleMoveRot(packet)

        return packet
    }

}

class CMovePosRotHandler : IncomingPacketHandler<ClientboundMoveEntityPosRotPacket> {

    override fun process(session: Session, packet: ClientboundMoveEntityPosRotPacket): ClientboundMoveEntityPosRotPacket {
        CacheSet.Entity.handleMovePosRot(packet)

        return packet
    }

}