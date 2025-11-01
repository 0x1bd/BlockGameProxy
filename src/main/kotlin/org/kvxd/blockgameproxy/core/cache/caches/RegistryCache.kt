package org.kvxd.blockgameproxy.core.cache.caches

import net.kyori.adventure.key.Key
import org.geysermc.mcprotocollib.protocol.data.game.KnownPack
import org.geysermc.mcprotocollib.protocol.data.game.RegistryEntry
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.cache.ResetCondition

object RegistryCache : Cache() {

    var tagsPacket: ClientboundUpdateTagsPacket? = null

    // Should be default though it can be changed
    var knownPacks = listOf(KnownPack("minecraft", "core", "1.21.4"))

    var registryData = mutableListOf<RegistryData>()

    override fun reset() {
        tagsPacket = null
        knownPacks = listOf(KnownPack("minecraft", "core", "1.21.4"))
        registryData.clear()
    }

    override val resetCondition: ResetCondition = ResetCondition.Disconnect

}

class RegistryData(val key: Key, val entries: List<RegistryEntry>)