package org.kvxd.blockgameproxy.core.cache.caches.chunk

import io.netty.buffer.Unpooled
import net.kyori.adventure.key.Key
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.codec.MinecraftTypes
import org.geysermc.mcprotocollib.protocol.data.game.chunk.ChunkSection
import org.geysermc.mcprotocollib.protocol.data.game.level.LightUpdateData
import org.geysermc.mcprotocollib.protocol.data.game.level.block.BlockEntityInfo
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundBlockEntityDataPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundBlockUpdatePacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundChunkBatchFinishedPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundChunkBatchStartPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundLevelChunkWithLightPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundLightUpdatePacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundSectionBlocksUpdatePacket
import org.kvxd.blockgameproxy.core.cache.SyncableCache
import java.util.concurrent.ConcurrentHashMap

object ChunkCache : SyncableCache() {

    // key = chunkPos
    private val chunks = ConcurrentHashMap<Long, Chunk>()

    var worldNames = arrayOf(Key.key("overworld"), Key.key("the_nether"), Key.key("the_end"))

    fun handleChunkPacket(packet: ClientboundLevelChunkWithLightPacket) {
        val chunkPos = Chunk.toLong(packet.x, packet.z)
        val sections = decodeSections(packet.chunkData)

        val chunk = Chunk(
            x = packet.x,
            z = packet.z,
            sections = sections,
            minSection = -4, // Overworld min section (-64)
            maxSection = 20, // Overworld max section (320)
            blockEntities = packet.blockEntities.toList(),
            lightUpdateData = packet.lightData
        )

        chunks[chunkPos] = chunk
    }

    fun handleBlockUpdate(packet: ClientboundBlockUpdatePacket) {
        val pos = packet.entry.position
        setBlockState(pos.x, pos.y, pos.z, packet.entry.block)
    }

    fun handleSectionBlocksUpdate(packet: ClientboundSectionBlocksUpdatePacket) {
        for (entry in packet.entries) {
            val pos = entry.position
            setBlockState(pos.x, pos.y, pos.z, entry.block)
        }
    }

    fun handleLightUpdatePacket(packet: ClientboundLightUpdatePacket) {
        val chunk = chunks[Chunk.toLong(packet.x, packet.z)] ?: return
        chunk.lightUpdateData = packet.lightData
    }

    fun setBlockState(x: Int, y: Int, z: Int, blockStateId: Int) {
        val chunkX = x shr 4
        val chunkZ = z shr 4
        val chunkPos = Chunk.toLong(chunkX, chunkZ)

        val chunk = chunks[chunkPos] ?: return
        val section = chunk.getChunkSection(y) ?: return

        section.setBlock(x and 15, y and 15, z and 15, blockStateId)
    }

    fun getBlockStateId(x: Int, y: Int, z: Int): Int {
        val chunkX = x shr 4
        val chunkZ = z shr 4

        val chunk = chunks[Chunk.toLong(chunkX, chunkZ)] ?: return 0
        return chunk.getBlockStateId(x and 15, y, z and 15)
    }

    fun applyLightUpdate(x: Int, z: Int, lightData: LightUpdateData) {
        val chunk = chunks[Chunk.toLong(x, z)] ?: return
        chunk.lightUpdateData = lightData
    }

    fun getChunk(x: Int, z: Int): Chunk? =
        chunks[Chunk.toLong(x, z)]

    fun unloadChunk(x: Int, z: Int) {
        chunks.remove(Chunk.toLong(x, z))
    }

    fun handleBlockEntityData(packet: ClientboundBlockEntityDataPacket) {
        val chunkX = packet.position.x shr 4
        val chunkZ = packet.position.z shr 4
        val chunk = chunks[Chunk.toLong(chunkX, chunkZ)] ?: return

        val updateBlockEntities = chunk.blockEntities.toMutableList()

        val existingIndex = updateBlockEntities.indexOfFirst {
            it.x == packet.position.x
            it.y == packet.position.y
            it.z == packet.position.z
        }

        val blockEntityInfo = BlockEntityInfo(
            packet.position.x,
            packet.position.y,
            packet.position.z,
            packet.type,
            packet.nbt
        )

        if (existingIndex >= 0) {
            updateBlockEntities[existingIndex] = blockEntityInfo
        }

        chunk.blockEntities = updateBlockEntities
    }

    fun buildChunkSyncPackets(): List<ClientboundLevelChunkWithLightPacket> {
        return chunks.values.map { chunk ->
            val buf = Unpooled.buffer()

            chunk.sections.forEach { section ->
                MinecraftTypes.writeChunkSection(buf, section)
            }

            val data = ByteArray(buf.readableBytes())
            buf.readBytes(data)
            buf.release()

            ClientboundLevelChunkWithLightPacket(
                chunk.x,
                chunk.z,
                data,
                Chunk.EMPTY_HEIGHT_MAP,
                chunk.blockEntities.toTypedArray(),
                chunk.lightUpdateData
                    ?: throw IllegalStateException("Light data is null for chunk ${chunk.x}, ${chunk.z}")
            )
        }
    }

    override fun sync(session: Session) {
        session.send(ClientboundChunkBatchStartPacket())

        val packets = buildChunkSyncPackets()

        packets.forEach(session::send)

        session.send(ClientboundChunkBatchFinishedPacket(packets.size))
    }

    override fun reset() {
        chunks.clear()
    }

    private fun decodeSections(chunkData: ByteArray): List<ChunkSection> {
        val buf = Unpooled.wrappedBuffer(chunkData)
        val sections = mutableListOf<ChunkSection>()
        val sectionCount = 24

        try {
            for (i in 0 until sectionCount) {
                if (buf.isReadable) {
                    sections.add(MinecraftTypes.readChunkSection(buf))
                } else {
                    sections.add(createEmptySection())
                }
            }
        } catch (e: Exception) {
            while (sections.size < sectionCount) {
                sections.add(createEmptySection())
            }
        } finally {
            buf.release()
        }

        return sections
    }

    private fun createEmptySection(): ChunkSection = ChunkSection()

}