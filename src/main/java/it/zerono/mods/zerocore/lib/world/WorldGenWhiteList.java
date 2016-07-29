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
    public void whiteListDimension(final int id) {
        this._dimensionsWhiteList.add(id);
    }

    private final Set<Integer> _dimensionsWhiteList;
}
