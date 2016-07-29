package it.zerono.mods.zerocore.lib.world;

import net.minecraft.world.World;

public interface IWorldGenWhiteList {

    /**
     * Check if worldgen is allowed in the provided World
     *
     * @param world the World to check
     * @return true if worldgen can be performed, false otherwise
     */
    boolean shouldGenerateIn(World world);

    /**
     * Whitelist the provided dimension ID, allowing worldgen in that dimension
     *
     * @param id the dimension to whitelist
     */
    void whiteListDimension(int id);

}
