package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundSetHeldSlotPacket
import org.kvxd.blockgameproxy.core.cache.Cache

class CSetHeldSlotHandler : IncomingPacketHandler<ClientboundSetHeldSlotPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundSetHeldSlotPacket
    ): ClientboundSetHeldSlotPacket {
        Cache.PLAYER.heldSlot = packet.slot

        return packet
    }
}