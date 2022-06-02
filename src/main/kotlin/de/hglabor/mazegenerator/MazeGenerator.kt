package de.hglabor.mazegenerator

import com.destroystokyo.paper.event.server.ServerTickStartEvent
import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigot

class MazeGenerator : KSpigot() {
    private var MAX_MS_PER_TICK = 15L
    val workloads = mutableListOf<IWorkload>()

    override fun load() {
        INSTANCE = this;
    }

    override fun startup() {
        LabyrinthCommand
        runCatching {
            listen<ServerTickStartEvent> {
                val stopTime = System.currentTimeMillis() + MAX_MS_PER_TICK
                while (workloads.isNotEmpty() && System.currentTimeMillis() <= stopTime) {
                    workloads.removeLastOrNull()?.execute()
                }
            }
        }.onFailure {
            logger.warning("Für den ...slowly command benötigst du paperspigot!")
            logger.warning("(muss aber nicht haha)")
        }
    }

    override fun shutdown() {
        logger.info("The Plugin was disabled!")
    }

    companion object {
        lateinit var INSTANCE: MazeGenerator
    }
}

val Manager by lazy { MazeGenerator.INSTANCE }
