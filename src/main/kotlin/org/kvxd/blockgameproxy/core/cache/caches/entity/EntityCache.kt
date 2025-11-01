package org.kvxd.blockgameproxy.core.cache.caches.entity

import org.cloudburstmc.math.vector.Vector3d
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.game.entity.type.EntityType
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundEntityPositionSyncPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityPosPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityPosRotPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundMoveEntityRotPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.ClientboundTeleportEntityPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerRotationPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.spawn.ClientboundAddEntityPacket
import org.kvxd.blockgameproxy.core.cache.SyncableCache

object EntityCache : SyncableCache() {

    // id = entity
    val entities = mutableMapOf<Int, Entity>()

    var clientPlayer: Entity? = null

    fun handleSpawnPacket(packet: ClientboundAddEntityPacket) {
        entities[packet.entityId] = Entity(
            packet.entityId,
            packet.uuid,
            packet.type,
            Vector3d.from(
                packet.x,
                packet.y,
                packet.z
            ),
            packet.pitch,
            packet.yaw,
            packet.headYaw,
            packet.data,
            Vector3d.from(
                packet.motionX,
                packet.motionY,
                packet.motionZ
            )
        )

        if (packet.type == EntityType.PLAYER) {

        }
    }

    fun handleSyncPacket(packet: ClientboundEntityPositionSyncPacket) {
        val entity = entities[packet.id] ?: return

        entity.pos = packet.position
        entity.velocity = packet.deltaMovement
        entity.yaw = packet.yRot
        entity.pitch = packet.xRot
        entity.onGround = packet.isOnGround
    }

    // ignoring position flags shouldn't be a problem
    fun handleTeleportPacket(packet: ClientboundTeleportEntityPacket) {
        val entity = entities[packet.id] ?: return

        entity.pos = packet.position
        entity.velocity = packet.deltaMovement
        entity.yaw = packet.yRot
        entity.pitch = packet.xRot
        entity.onGround = packet.isOnGround
    }

    fun handleMovePos(packet: ClientboundMoveEntityPosPacket) {
        val entity = entities[packet.entityId] ?: return

        entity.pos.add(
            packet.moveX,
            packet.moveY,
            packet.moveZ,
        )

        entity.onGround = packet.isOnGround
    }

    fun handleMoveRot(packet: ClientboundMoveEntityRotPacket) {
        val entity = entities[packet.entityId] ?: return

        entity.yaw = packet.yaw
        entity.pitch = packet.pitch

        entity.onGround = packet.isOnGround
    }

    fun handleMovePosRot(packet: ClientboundMoveEntityPosRotPacket) {
        val entity = entities[packet.entityId] ?: return

        entity.pos.add(
            packet.moveX,
            packet.moveY,
            packet.moveZ,
        )

        entity.yaw = packet.yaw
        entity.pitch = packet.pitch

        entity.onGround = packet.isOnGround
    }

    fun handlePlayerRot(packet: ClientboundPlayerRotationPacket) {
        checkNotNull(clientPlayer)

        clientPlayer!!.yaw = packet.xRot
        clientPlayer!!.pitch = packet.yRot
    }

    fun buildSpawnSyncPackets(): List<ClientboundAddEntityPacket> {
        return entities.values.map { entity ->
            ClientboundAddEntityPacket(
                entity.id,
                entity.uuid,
                entity.type,
                entity.data,
                entity.pos.x,
                entity.pos.y,
                entity.pos.z,
                entity.pitch,
                entity.yaw,
                entity.headYaw,
                entity.velocity.x,
                entity.velocity.y,
                entity.velocity.z,
            )
        }
    }

    override fun sync(session: Session) {
        buildSpawnSyncPackets().forEach(session::send)
    }

    override fun reset() {
        entities.clear()
    }

}