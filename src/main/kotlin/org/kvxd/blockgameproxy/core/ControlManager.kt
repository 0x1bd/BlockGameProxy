package org.kvxd.blockgameproxy.core

object ControlManager {

    var controller: ControlParty = ControlParty.CLIENT

    fun transferToServerSession() {
        controller = ControlParty.SERVER_SESSION
    }

    fun transferToClient() {
        controller = ControlParty.CLIENT
    }

    fun isControlledByServer(): Boolean = controller == ControlParty.SERVER_SESSION
    fun isControlledByClient(): Boolean = controller == ControlParty.CLIENT
}
