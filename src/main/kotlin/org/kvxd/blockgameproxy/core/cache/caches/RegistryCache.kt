package org.kvxd.blockgameproxy.core.cache.caches

import net.kyori.adventure.key.Key
import org.geysermc.mcprotocollib.protocol.data.game.KnownPack
import org.geysermc.mcprotocollib.protocol.data.game.RegistryEntry
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket

class RegistryCache(
    var knownPacks: List<KnownPack> = listOf(KnownPack("minecraft", "core", "1.21.4")),
    var registryData: MutableList<RegistryData> = mutableListOf(),
    // Workaround: Cant instantiate ClientboundUpdateTagsPacket with tags???
    var tagsPacket: ClientboundUpdateTagsPacket? = null
)

class RegistryData(val key: Key, val entries: List<RegistryEntry>)