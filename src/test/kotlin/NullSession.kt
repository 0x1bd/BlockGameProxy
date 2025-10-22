import io.netty.channel.Channel
import net.kyori.adventure.text.Component
import org.geysermc.mcprotocollib.network.Flag
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.compression.CompressionConfig
import org.geysermc.mcprotocollib.network.crypt.EncryptionConfig
import org.geysermc.mcprotocollib.network.event.session.SessionEvent
import org.geysermc.mcprotocollib.network.event.session.SessionListener
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import java.net.SocketAddress
import java.util.concurrent.Executor
import java.util.function.Supplier

class NullSession : Session {

    override fun getLocalAddress(): SocketAddress? = null

    override fun getRemoteAddress(): SocketAddress? = null

    override fun getPacketProtocol(): MinecraftProtocol? = null

    override fun getFlags(): Map<String?, Any?>? = null

    override fun hasFlag(flag: Flag<*>?): Boolean = false

    override fun <T : Any?> getFlagSupplied(
        flag: Flag<T?>?,
        defSupplier: Supplier<T?>?
    ): T? = null

    override fun <T : Any?> setFlag(flag: Flag<T?>?, value: T?) {

    }

    override fun setFlags(flags: Map<String?, Any?>?) {

    }

    override fun getListeners(): List<SessionListener?>? = null

    override fun addListener(listener: SessionListener?) {

    }

    override fun removeListener(listener: SessionListener?) {

    }

    override fun callEvent(event: SessionEvent?) {

    }

    override fun callPacketReceived(packet: Packet?) {

    }

    override fun callPacketSent(packet: Packet?) {

    }

    override fun setCompression(compressionConfig: CompressionConfig?) {

    }

    override fun setEncryption(encryptionConfig: EncryptionConfig?) {

    }

    override fun isConnected(): Boolean = false

    override fun send(packet: Packet, onSent: Runnable?) {

    }

    override fun disconnect(reason: Component, cause: Throwable?) {

    }

    override fun setAutoRead(autoRead: Boolean) {

    }

    override fun getChannel(): Channel? = null

    override fun getPacketHandlerExecutor(): Executor? = null
}