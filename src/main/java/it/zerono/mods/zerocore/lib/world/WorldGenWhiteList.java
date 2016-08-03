package it.zerono.mods.zerocore.lib.world;

import net.minecraft.world.World;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class WorldGenWhiteList implements IWorldGenWhiteList {

    public WorldGenWhiteList() {
        this._dimensionsWhiteList = new CopyOnWriteArraySet<Integer>();
    }

    @Override
    public boolean shouldGenerateIn(final World world) {
        return this._dimensionsWhiteList.contains(world.provider.getDimension());
    }

    @Override
    public boolean shouldGenerateIn(int dimensionId) {
        return this._dimensionsWhiteList.contains(dimensionId);
    }

    @Override
    public void whiteListDimension(final int id) {
        this._dimensionsWhiteList.add(id);
    }

    @Override
    public void whiteListDimensions(int[] ids) {

        for (int i = 0; i < ids.length; ++i)
            this._dimensionsWhiteList.add(ids[i]);
    }

    @Override
    public void clearWhiteList() {
        this._dimensionsWhiteList.clear();
    }

    private final Set<Integer> _dimensionsWhiteList;
}
