package org.kvxd.blockgameproxy.core.cache.caches.chunk

import io.netty.buffer.Unpooled
import org.geysermc.mcprotocollib.protocol.codec.MinecraftTypes
import org.geysermc.mcprotocollib.protocol.data.game.chunk.ChunkSection
import org.geysermc.mcprotocollib.protocol.data.game.level.LightUpdateData
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundBlockUpdatePacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundLevelChunkWithLightPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundSectionBlocksUpdatePacket
import java.util.concurrent.ConcurrentHashMap

class ChunkCache {

    // key = chunkPos
    private val chunks = ConcurrentHashMap<Long, Chunk>()

    fun handleChunkPacket(packet: ClientboundLevelChunkWithLightPacket) {
        val chunkPos = Chunk.chunkPosToLong(packet.x, packet.z)
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

    fun handleLightUpdate(x: Int, z: Int, lightData: LightUpdateData) {
        val chunk = chunks[Chunk.chunkPosToLong(x, z)] ?: return
        chunk.lightUpdateData = lightData
    }

    fun setBlockState(x: Int, y: Int, z: Int, blockStateId: Int) {
        val chunkX = x shr 4
        val chunkZ = z shr 4
        val chunkPos = Chunk.chunkPosToLong(chunkX, chunkZ)

        val chunk = chunks[chunkPos] ?: return
        val section = chunk.getChunkSection(y) ?: return

        section.setBlock(x and 15, y and 15, z and 15, blockStateId)
    }

    fun getBlockStateId(x: Int, y: Int, z: Int): Int {
        val chunkX = x shr 4
        val chunkZ = z shr 4

        val chunk = chunks[Chunk.chunkPosToLong(chunkX, chunkZ)] ?: return 0
        return chunk.getBlockStateId(x and 15, y, z and 15)
    }

    fun applyLightUpdate(x: Int, z: Int, lightData: LightUpdateData) {
        val chunk = chunks[Chunk.chunkPosToLong(x, z)] ?: return
        chunk.lightUpdateData = lightData
    }

    fun getChunk(x: Int, z: Int): Chunk? =
        chunks[Chunk.chunkPosToLong(x, z)]

    fun unloadChunk(x: Int, z: Int) {
        chunks.remove(Chunk.chunkPosToLong(x, z))
    }

    fun buildChunkSyncPackets(): List<ClientboundLevelChunkWithLightPacket> {
        return chunks.values.map { chunk ->
            val buf = Unpooled.buffer()
            chunk.sections.forEach { MinecraftTypes.writeChunkSection(buf, it) }

            val chunkData = ByteArray(buf.readableBytes())
            buf.readBytes(chunkData)
            buf.release()

            ClientboundLevelChunkWithLightPacket(
                chunk.x,
                chunk.z,
                chunkData,
                Chunk.EMPTY_HEIGHT_MAP,
                chunk.blockEntities.toTypedArray(),
                chunk.lightUpdateData!!
            )
        }
    }

    private fun decodeSections(chunkData: ByteArray): List<ChunkSection> {
        val buf = Unpooled.wrappedBuffer(chunkData)
        val sections = mutableListOf<ChunkSection>()

        while (buf.isReadable) {
            try {
                sections.add(MinecraftTypes.readChunkSection(buf))
            } catch (_: Exception) {
                // Stop reading if malformed or incomplete section
                break
            }
        }

        buf.release()
        return sections
    }

}