package org.kvxd.blockgameproxy.core.cache.caches.chunk

import org.cloudburstmc.nbt.NbtMap
import org.geysermc.mcprotocollib.protocol.data.game.chunk.ChunkSection
import org.geysermc.mcprotocollib.protocol.data.game.level.LightUpdateData
import org.geysermc.mcprotocollib.protocol.data.game.level.block.BlockEntityInfo

class Chunk(
    val x: Int,
    val z: Int,
    val sections: List<ChunkSection>,
    val maxSection: Int,
    val minSection: Int,
    val blockEntities: List<BlockEntityInfo>,
    var lightUpdateData: LightUpdateData?
) {

    companion object {
        private const val UINT_MASK: Long = 4294967295L

        val EMPTY_HEIGHT_MAP = NbtMap.EMPTY

        fun chunkPosToLong(x: Int, z: Int): Long {
            return (x.toLong() and UINT_MASK) or ((z.toLong() and UINT_MASK) shl 32)
        }

        fun longToChunkX(l: Long): Int {
            return (l and UINT_MASK).toInt()
        }

        fun longToChunkZ(l: Long): Int {
            return ((l shr 32) and UINT_MASK).toInt()
        }
    }

    fun getChunkPos() = chunkPosToLong(x, z)

    fun getBlockStateId(relativeX: Int, y: Int, relativeZ: Int): Int {
        val section = getChunkSection(y) ?: return 0
        return section.getBlock(relativeX, y and 15, relativeZ)
    }

    fun getChunkSection(y: Int): ChunkSection? {
        val sectionIndex = getSectionIndex(y)
        return if (sectionIndex in sections.indices) sections[sectionIndex] else null
    }

    fun getSectionIndex(y: Int): Int = (y shr 4) - minSection

    fun minY(): Int = minSection shl 4

    fun maxY(): Int = (maxSection shl 4) - 1

    fun getSectionsCount(): Int = sections.size

}