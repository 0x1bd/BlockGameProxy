package org.kvxd.blockgameproxy.core

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.compression.CompressionConfig
import org.geysermc.mcprotocollib.network.compression.ZlibCompression
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.handshake.HandshakeIntent

fun createMinecraftProtocol(): MinecraftProtocol =
    MinecraftProtocol().apply { isUseDefaultListeners = false }

fun Session.switchState(state: ProtocolState) {
    switchInboundState { packetProtocol.inboundState = state }
    switchOutboundState { packetProtocol.outboundState = state }
}

fun Session.switchInbound(state: ProtocolState) {
    switchInboundState { packetProtocol.inboundState = state }
}

fun Session.switchOutbound(state: ProtocolState) {
    switchOutboundState { packetProtocol.outboundState = state }
}

fun HandshakeIntent.toProtocolState(): ProtocolState =
    when (this) {
        HandshakeIntent.STATUS -> ProtocolState.STATUS
        HandshakeIntent.LOGIN, HandshakeIntent.TRANSFER -> ProtocolState.LOGIN
    }

fun createCompression(threshold: Int): CompressionConfig =
    CompressionConfig(threshold, ZlibCompression(), false)

fun Session.setCompressionThreshold(threshold: Int) {
    if (threshold >= 0)
        setCompression(createCompression(threshold))
}