package it.zerono.mods.zerocore.lib.world;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public abstract class ModWorldGeneratorBase implements IWorldGenerator {

    @Override
    public final void generate(final Random random, final int chunkX, final int chunkZ, final World world,
                         final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {

        if ((null == this._whiteList) || !this._whiteList.shouldGenerateIn(world))
            return;

        this.generateChunk(random, chunkX << 4, chunkZ << 4, world, chunkGenerator, chunkProvider);
    }

    protected abstract void generateChunk(final Random random, final int firstBlockX, final int firstBlockZ,
                                          final World world, final IChunkGenerator chunkGenerator,
                                          final IChunkProvider chunkProvider);

    protected ModWorldGeneratorBase(final IWorldGenWhiteList whiteList) {
        this._whiteList = whiteList;
    }

    protected final IWorldGenWhiteList _whiteList;
}
