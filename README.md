# BlockGameProxy
BlockGameProxy is the name of a proxy designed to bypass the queue on 2b2t, because apparently waiting is for people who aren’t “built different.” 

Inspired by [ZenithProxy](https://github.com/rfresh2/ZenithProxy) and [2based2wait](https://github.com/Enchoseon/2based2wait).

## Explanation
This proxy consists of two components: a proxy server and a proxy client. Players connect to the proxy server like a normal minecraft server, while the proxy client connects to the destination server (e.g., 2b2t.org). The proxy forwards all player actions to the destination server. When no players are connected, the proxy client acts as a bot.

