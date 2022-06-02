package de.hglabor.mazegenerator

import com.mojang.brigadier.arguments.IntegerArgumentType
import net.axay.kspigot.chat.KColors
import net.axay.kspigot.commands.*
import net.axay.kspigot.extensions.broadcast
import net.axay.kspigot.extensions.geometry.SimpleLocation3D
import net.axay.kspigot.extensions.geometry.toSimpleString
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

object LabyrinthCommand {
    private val blockPalette = RandomCollection<Material>()
        .add(70.0, Material.STONE)
        .add(15.0, Material.COBBLESTONE)
        .add(10.0, Material.STONE_BRICKS)
        .add(2.0, Material.MOSSY_STONE_BRICKS)
        .add(3.0, Material.CRACKED_STONE_BRICKS);

    init {
        command("labyrinth") {
            requires { it.bukkitSender.isOp }
            argument<Int>("size", IntegerArgumentType.integer(1, 1000)) {
                suggestList { listOf(1, 5, 10, 100, 500) }
                argument<Int>("scale", IntegerArgumentType.integer(1, 1000)) {
                    suggestList { listOf(1, 5, 10, 100, 500) }
                    argument<Int>("maxHeight", IntegerArgumentType.integer(1, 256)) {
                        suggestList { listOf(1, 5, 10, 100, 500) }
                        runs {
                            this.world.generateMaze(this.player.location,
                                getArgument("size"),
                                getArgument("scale"),
                                getArgument("maxHeight")
                            )
                        }
                        literal("slowly") {
                            runs {
                                this.world.generateMaze(this.player.location,
                                    getArgument("size"),
                                    getArgument("scale"),
                                    getArgument("maxHeight"),
                                    true
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun World.generateMaze(center: Location, size: Int, scale: Int, maxHeight: Int, slowly: Boolean = false) {
        val offSet = size / 2
        val maze = Maze(size, size)
        while (!maze.generate()); //lol

        val scaledMaze: Array<BooleanArray> = Array(size * scale) { BooleanArray(size * scale) }

        for (i in scaledMaze.indices) {
            for (j in 0 until scaledMaze[0].size) {
                scaledMaze[i][j] = maze.maze.get(i / scale * maze.width + j / scale)
            }
        }

        broadcast("${KColors.GREEN}Generating Maze at ${center.toSimpleString()}")

        for (i in scaledMaze.indices) {
            for (j in 0 until scaledMaze[0].size) {
                if (scaledMaze[i][j]) {
                    val x = center.blockX + i - offSet * scale
                    val z = center.blockZ + j - offSet * scale
                    for (y in minHeight until maxHeight) {
                        if (slowly) {
                            Manager.workloads.add(BlockPlaceWorkload(this,
                                SimpleLocation3D(x, y, z),
                                blockPalette.next())
                            )
                        } else {
                            setBlockData(x, y, z, blockPalette.next().createBlockData())
                        }
                    }
                }
            }
        }
    }
}
