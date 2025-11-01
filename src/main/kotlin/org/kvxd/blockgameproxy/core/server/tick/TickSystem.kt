package org.kvxd.blockgameproxy.core.server.tick

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import kotlin.time.Duration.Companion.milliseconds

object TickSystem : CoroutineScope {

    private val LOGGER = LoggerFactory.getLogger("Ticker")

    override val coroutineContext = SupervisorJob() + Dispatchers.Default

    var currentTick = 0
    private val registeredTasks = mutableListOf<TickTask>()

    const val TICK_INTERVAL_MS = 50L

    private var tickJob: Job? = null

    fun start() {
        if (tickJob?.isActive == true)
            return

        tickJob = launch {
            var tickCount = 0
            var tickInterval = TICK_INTERVAL_MS.milliseconds

            while (isActive) {
                val tickStart = System.currentTimeMillis()

                for (task in registeredTasks) {
                    try {
                        task.tick(tickCount)
                    } catch (e: Exception) {
                        LOGGER.error("Tick task failed", e)
                    }
                }

                currentTick = tickCount
                tickCount++

                val processingTime = System.currentTimeMillis() - tickStart
                val delayTime = tickInterval - processingTime.milliseconds

                if (delayTime.isPositive())
                    delay(delayTime)
                else {
                    // running behind, log if more than 10ms lag
                    if (delayTime < (-10).milliseconds) {
                        LOGGER.trace("Tick running behind by ${-delayTime.inWholeMilliseconds}ms")
                    }
                }
            }
        }
    }

    fun stop() {
        tickJob?.cancel()
        currentTick = 0
    }

    fun registerTask(task: TickTask) {
        registeredTasks.add(task)
    }

    fun unregisterTask(task: TickTask) {
        registeredTasks.remove(task)
    }

    fun getRegisteredTasks(): List<TickTask> = registeredTasks.toList()

    fun isRunning(): Boolean = tickJob?.isActive == true

}