fun main() {
    val bindHost = "0.0.0.0"
    val bindPort = 25566
    val remoteHost = "127.0.0.1"
    val remotePort = 25565

    val server = ProxyServer(bindHost, bindPort, remoteHost, remotePort)

    server.start()
}