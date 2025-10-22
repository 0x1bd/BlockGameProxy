import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.handler.OutgoingPacketHandler
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistry
import org.kvxd.blockgameproxy.core.handler.PostOutgoingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class PacketHandlerRegistryTest {

    @Test
    fun `test all register and get methods`() {
        val registry = PacketHandlerRegistry()

        var incomingHandled = false
        var outgoingHandled = false
        var postOutgoingHandled = false

        registry.registerIncoming(object : IncomingPacketHandler<DummyIncomingPacket> {
            override fun handle(session: Session, packet: DummyIncomingPacket): DummyIncomingPacket {
                incomingHandled = true
                return packet
            }
        })

        registry.registerOutgoing(object : OutgoingPacketHandler<DummyOutgoingPacket> {
            override fun handle(session: Session, packet: DummyOutgoingPacket): DummyOutgoingPacket {
                outgoingHandled = true
                return packet
            }
        })

        registry.registerPostOutgoing(object : PostOutgoingPacketHandler<DummyPostOutgoingPacket> {
            override fun handle(session: Session, packet: DummyPostOutgoingPacket): DummyPostOutgoingPacket {
                postOutgoingHandled = true
                return packet
            }
        })

        val incomingHandler = registry.getIncoming(DummyIncomingPacket::class)
        assertNotNull(incomingHandler)
        val incomingPacket = DummyIncomingPacket()
        val resultIncoming = incomingHandler.handle(NullSession(), incomingPacket)
        assertSame(incomingPacket, resultIncoming)
        assertTrue(incomingHandled)

        val outgoingHandler = registry.getOutgoing(DummyOutgoingPacket::class)
        assertNotNull(outgoingHandler)
        val outgoingPacket = DummyOutgoingPacket()
        val resultOutgoing = outgoingHandler.handle(NullSession(), outgoingPacket)
        assertSame(outgoingPacket, resultOutgoing)
        assertTrue(outgoingHandled)

        val postHandler = registry.getPostOutgoing(DummyPostOutgoingPacket::class)
        assertNotNull(postHandler)
        val postPacket = DummyPostOutgoingPacket()
        val resultPost = postHandler.handle(NullSession(), postPacket)
        assertSame(postPacket, resultPost)
        assertTrue(postOutgoingHandled)
    }
}