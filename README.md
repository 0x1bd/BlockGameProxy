# Block Game Proxy

A proxy for block game that allows being online 24/7.

This proxy consists of two components: a proxy server and a
proxy client. Players connect to the proxy server like a normal
minecraft server, while the proxy client connects to the destination
server (e.g, 2b2t.org). The proxy forwards all player actions to the
destination server. When no players are connected, the proxy client acts as a bot.

BGP will likely only support the current version the server `2b2t.org` is on since that is the primary target server. However I plan to integrate a plugin system which can be used to expand functionality as far as supporting more versions.

## Specification

Specification of specific parts of the proxy can be viewed below.

### Bootstrap

The Bootstrap launches the proxy client and proxy server (in that order). And manages configuration and logging.

### Proxy Client

The proxy client connects to the target server and populates the shared cache with world data, playerdata, entitydata and so on.

Once the proxy client connects to the target server it starts by sending some ordinary login packets and then populates the shared caches using data received by the target server. After a `Game Event` packet has been sent with the appropriate event (`13, null`), the client will join the server world and continue populating caches. At this stage most cached packets are: Chunk Data, Entity Data, SetTIme and so forth.

Once a user connects to the proxy server, the client will forward packets sent by the target server. At this stage, data will still be cached in case the user disconnects and reconnects.

### Proxy Server

The proxy server boots a network server that is bound after the client has connected to the target server. If the client fails to connect to its target the server will never be bound.

Once the proxy server establishes a connection with a user it also populates shared caches, however this data is not strictly required. After a user has connected, the server will send required login data, chunk data etc. This also includes encryption and compression packets. The proxy server will also forward almost all `GAME` packets to the proxy client in order to be sent to the target server. This excludes packets such as `KeepAlivePacket` since the `id` will be mismatched. The proxy client handles these specific packets manually.

### Cache

The cache (also known as **shared cache**) is the system responsible for holding specific data for an unknown amount of time. This is useful for both proxy server and proxy client since both require some data from the other however they cannot directly communicate. A cache can be read and populated (wrote to). Another functionality of the cache system is its ability to be reset. An example implementation of a cache can be viewed below.

```kotlin
abstract class Cache() {
	abstract fun populate()
	
	abstract val resetCondition: ResetCondition
}

enum class ResetCondition {
	USER_CONNECT,
	USER_DISCONNECT,
	SERVER_LOGIN_PACKET
}
```

## Resources

Resources used by me in developing this program were:
[Java Edition protocol – Minecraft Wiki (1.21.4)](https://minecraft.wiki/w/Java_Edition_protocol/Packets?oldid=2938097)

[GitHub - rfresh2/ZenithProxy](https://github.com/rfresh2/ZenithProxy)