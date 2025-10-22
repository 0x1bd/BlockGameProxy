package org.kvxd.blockgameproxy.core.shared

import net.kyori.adventure.key.Key
import org.geysermc.mcprotocollib.protocol.data.game.KnownPack
import org.geysermc.mcprotocollib.protocol.data.game.RegistryEntry
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.PlayerSpawnInfo
import org.geysermc.mcprotocollib.protocol.data.game.setting.Difficulty
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundUpdateRecipesPacket

object SharedData {

    var enabledFeatures: Array<Key>? = null
    var knownPacks: List<KnownPack>? = null

    class RegistryData(val key: Key, val entries: List<RegistryEntry>)
    val registryData: MutableList<RegistryData> = mutableListOf()

    val tags: MutableMap<Key, MutableMap<Key, IntArray>> = mutableMapOf()

    class LoginData(
        val entityId: Int,
        val hardcore: Boolean,
        val worldNames: Array<Key>,
        val maxPlayers: Int,
        val viewDistance: Int,
        val simulationDistance: Int,
        val reducedDebugInfo: Boolean,
        val enableRespawnScreen: Boolean,
        val doLimitedCrafting: Boolean,
        val commonPlayerSpawnInfo: PlayerSpawnInfo,
        val enforcesSecureChat: Boolean
    )
    var loginData: LoginData? = null

    class DifficultyData(
        val difficulty: Difficulty,
        val difficultyLocked: Boolean
    )
    var difficultyData: DifficultyData? = null

    class PlayerData(
        var invincible: Boolean,
        var canFly: Boolean,
        var flying: Boolean,
        var creative: Boolean,
        var flySpeed: Float,
        var walkSpeed: Float
    )
    var playerData: PlayerData? = null
    var heldSlot: Int = 0

    var itemSets: MutableMap<Key, IntArray> = mutableMapOf()
    var stonecutterRecipes: MutableList<ClientboundUpdateRecipesPacket.SelectableRecipe> = mutableListOf()


}