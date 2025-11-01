package org.kvxd.blockgameproxy.core.cache

import org.geysermc.mcprotocollib.network.Session

abstract class SyncableCache : Cache() {

    abstract fun sync(session: Session)

}