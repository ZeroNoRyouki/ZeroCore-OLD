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
     * Check if worldgen is allowed in the provided dimension
     *
     * @param dimensionId the dimension ID to check
     * @return true if worldgen can be performed, false otherwise
     */
    boolean shouldGenerateIn(int dimensionId);

    /**
     * Whitelist the provided dimension ID, allowing worldgen in that dimension
     *
     * @param dimensionId the dimension to whitelist
     */
    void whiteListDimension(int dimensionId);

    /**
     * Whitelist the provided dimension IDs, allowing worldgen in those dimensions
     *
     * @param dimensionIds the dimensions to whitelist
     */
    void whiteListDimensions(int[] dimensionIds);

    /**
     * Remove all dimension IDs from the white list
     */
    void clearWhiteList();
}
