package com.p3achb0t.client.communication.sockets

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.lang.Thread.sleep
import java.net.InetSocketAddress
import java.net.URI
import java.util.*


class Server(port: Int, var callbacks: ArrayList<(String) -> Unit>) : WebSocketServer(InetSocketAddress(port)) {

    init {
        this.start()
    }

    override fun onOpen(p0: WebSocket?, p1: ClientHandshake?) {
        println("Server onOpen")
    }

    override fun onClose(p0: WebSocket?, p1: Int, p2: String?, p3: Boolean) {
        println("onClose")
    }

    override fun onMessage(socket: WebSocket?, message: String?) {
        println("onMessage $message")
        callbacks.forEach {
            it(message ?: "")
        }
    }

    override fun onStart() {

    }

    override fun onError(p0: WebSocket?, p1: Exception?) {
        println("onError")
    }


}

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val port = 8274
        val server = Server(port, ArrayList<(String) -> Unit>())
        sleep(1000)

        val client = CommunicationClient(URI("ws://localhost:$port"))
        client.connect()
        sleep(1000)
        client.send("Hello world")

    }
}