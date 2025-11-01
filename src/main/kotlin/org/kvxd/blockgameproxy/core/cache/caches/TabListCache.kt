package org.kvxd.blockgameproxy.core.cache.caches

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.game.PlayerListEntry
import org.geysermc.mcprotocollib.protocol.data.game.PlayerListEntryAction
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundPlayerInfoRemovePacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundPlayerInfoUpdatePacket
import org.kvxd.blockgameproxy.core.cache.SyncableCache
import java.util.*

object TabListCache : SyncableCache() {

    private val entriesById = mutableMapOf<UUID, PlayerListEntry>()

    val entries: Collection<PlayerListEntry>
        get() = entriesById.values

    fun handleUpdatePacket(packet: ClientboundPlayerInfoUpdatePacket) {
        for (entry in packet.entries) {
            for (action in packet.actions) {
                when (action) {
                    PlayerListEntryAction.ADD_PLAYER -> {
                        entriesById[entry.profileId] = entry
                    }

                    PlayerListEntryAction.INITIALIZE_CHAT -> {
                        entriesById[entry.profileId]?.apply {
                            sessionId = entry.sessionId
                            expiresAt = entry.expiresAt
                            publicKey = entry.publicKey
                            keySignature = entry.keySignature
                        }
                    }

                    PlayerListEntryAction.UPDATE_GAME_MODE -> {
                        entriesById[entry.profileId]?.gameMode = entry.gameMode
                    }

                    PlayerListEntryAction.UPDATE_LISTED -> {
                        entriesById[entry.profileId]?.isListed = entry.isListed
                    }

                    PlayerListEntryAction.UPDATE_LATENCY -> {
                        entriesById[entry.profileId]?.latency = entry.latency
                    }

                    PlayerListEntryAction.UPDATE_DISPLAY_NAME -> {
                        entriesById[entry.profileId]?.displayName = entry.displayName
                    }

                    PlayerListEntryAction.UPDATE_LIST_ORDER -> {
                        entriesById[entry.profileId]?.listOrder = entry.listOrder
                    }

                    PlayerListEntryAction.UPDATE_HAT -> {
                        entriesById[entry.profileId]?.isShowHat = entry.isShowHat
                    }
                }
            }
        }
    }

    fun handleRemovePacket(packet: ClientboundPlayerInfoRemovePacket) {
        packet.profileIds.forEach { id ->
            entriesById.remove(id)
        }
    }

    override fun sync(session: Session) {
        if (entriesById.isEmpty()) return

        val packet = ClientboundPlayerInfoUpdatePacket(
            EnumSet.of(
                PlayerListEntryAction.ADD_PLAYER,
                PlayerListEntryAction.INITIALIZE_CHAT,
                PlayerListEntryAction.UPDATE_GAME_MODE,
                PlayerListEntryAction.UPDATE_LISTED,
                PlayerListEntryAction.UPDATE_LATENCY,
                PlayerListEntryAction.UPDATE_DISPLAY_NAME,
                PlayerListEntryAction.UPDATE_LIST_ORDER,
                PlayerListEntryAction.UPDATE_HAT
            ),
            entriesById.values.toTypedArray()
        )

        session.send(packet)
    }

    override fun reset() {
        entriesById.clear()
    }

}
