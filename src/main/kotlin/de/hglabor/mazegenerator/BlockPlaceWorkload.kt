package de.hglabor.mazegenerator

import net.axay.kspigot.extensions.geometry.SimpleLocation3D
import net.minecraft.core.BlockPosition
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld
import org.bukkit.craftbukkit.v1_17_R1.block.data.CraftBlockData

data class BlockPlaceWorkload(val world: World, val location: SimpleLocation3D, val material: Material) : IWorkload {
    private val state = (material.createBlockData() as CraftBlockData).state
    private fun setBlockInNativeWorld(applyPhysics: Boolean = false) {
        val nmsWorld = (world as CraftWorld).handle
        val bp = BlockPosition(location.x, location.y, location.z)
        nmsWorld.setTypeAndData(bp, state, if (applyPhysics) 3 else 2)
    }

    override fun execute() {
        setBlockInNativeWorld()
    }
}
