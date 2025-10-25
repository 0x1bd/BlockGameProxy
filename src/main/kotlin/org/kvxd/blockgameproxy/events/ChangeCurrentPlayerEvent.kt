package org.kvxd.blockgameproxy.events

import com.kvxd.eventbus.Event
import org.geysermc.mcprotocollib.network.Session

class ChangeCurrentPlayerEvent(val player: Session?) : Event