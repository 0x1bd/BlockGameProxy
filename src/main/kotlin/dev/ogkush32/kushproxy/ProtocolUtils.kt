package dev.ogkush32.kushproxy

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState

fun Session.switchState(state: ProtocolState) {
    switchInboundState {
        packetProtocol.inboundState = state
    }
    switchOutboundState {
        packetProtocol.outboundState = state
    }
}