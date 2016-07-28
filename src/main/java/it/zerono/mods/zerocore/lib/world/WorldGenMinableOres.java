package it.zerono.mods.zerocore.lib.world;

import com.google.common.collect.Lists;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WorldGenMinableOres implements IWorldGenerator {

    public void addOre(Block ore, Block blockToReplace, int minY, int maxY,
                       int minBlockCount, int maxBlockCount, int iterations) {

        this.addOre(ore.getDefaultState(), blockToReplace.getDefaultState(), minY, maxY, minBlockCount, maxBlockCount, iterations);
    }

    public void addOre(IBlockState ore, IBlockState blockToReplace, int minY, int maxY,
                       int minBlockCount, int maxBlockCount, int iterations) {

        if (null == this._generators)
            this._generators = Lists.newArrayList();

        this._generators.add(new VeinGenerator(ore, blockToReplace, minY, maxY, minBlockCount, maxBlockCount, iterations));
    }

    @Override
    public void generate(final Random random, final int chunkX, final int chunkZ, final World world,
                         final IChunkGenerator chunkGenerator, final IChunkProvider chunkProvider) {

        if ((null == this._generators) || (this._generators.isEmpty()))
            return;

        final int x = chunkX << 4;
        final int z = chunkZ << 4;
        final BlockPos position = new BlockPos(x, 0, z);

        for (int i = 0; i < this._generators.size(); ++i)
            this._generators.get(i).generate(world, random, position);
    }

    private List<VeinGenerator> _generators;

    private class VeinGenerator extends WorldGenerator {

        VeinGenerator(final IBlockState ore, final IBlockState blockToReplace, final int minY, final int maxY,
                      final int minBlockCount, final int maxBlockCount, final int iterations) {

            super(false);

            if ((null == ore) || (null == blockToReplace))
                throw new IllegalArgumentException();

            if ((minY < 0) || (maxY < minY) || (maxY > 256))
                throw new IllegalArgumentException("Invalid Y coordinates");

            if ((minBlockCount < 0) || (maxBlockCount < minBlockCount))
                throw new IllegalArgumentException("Invalid vein size");

            if (iterations <= 0)
                throw new IllegalArgumentException("Invalid number of iterations");

            final Block targetBlock = blockToReplace.getBlock();

            this._replaceCheck = new Predicate<IBlockState>() {
                @Override
                public boolean apply(@Nullable IBlockState input) {
                    return null != input &&
                            input.getBlock() == targetBlock &&
                            input.getBlock().getMetaFromState(input) == targetBlock.getMetaFromState(input);
                }
            };

            this._oreBlock = ore;
            this._minBlocksCount = minBlockCount;
            this._maxBlocksCount = maxBlockCount;
            this._minY = minY;
            this._maxY = maxY;
            this._iterations = iterations;
        }

        @Override
        public boolean generate(final World world, final Random random, final BlockPos position) {

            final int x = position.getX();
            final int z = position.getZ();

            for (int i = 0; i < this._iterations; ++i)
                this.generateVein(world, random, x, z);

            return false;
        }

        private void generateVein(World world, Random random, int x, int z) {

            final int blocksCount = this._minBlocksCount + random.nextInt(this._maxBlocksCount - this._minBlocksCount);
            final int y = this._minY + random.nextInt(this._maxY - this._minY);

            final float f = random.nextFloat() * (float)Math.PI;  // 0.0<>1.0 * PI
            final double d0 = (double)((float)(x + 8) + MathHelper.sin(f) * (float)blocksCount / 8.0F);
            final double d1 = (double)((float)(x + 8) - MathHelper.sin(f) * (float)blocksCount / 8.0F);
            final double d2 = (double)((float)(z + 8) + MathHelper.cos(f) * (float)blocksCount / 8.0F);
            final double d3 = (double)((float)(z + 8) - MathHelper.cos(f) * (float)blocksCount / 8.0F);
            final double d4 = (double)(y + random.nextInt(3) - 2);
            final double d5 = (double)(y + random.nextInt(3) - 2);

            for (int i = 0; i < blocksCount; ++i) {

                final float f1 = (float)i / (float)blocksCount;
                final double d6 = d0 + (d1 - d0) * (double)f1;
                final double d7 = d4 + (d5 - d4) * (double)f1;
                final double d8 = d2 + (d3 - d2) * (double)f1;
                final double d9 = random.nextDouble() * (double)blocksCount / 16.0D;
                final double d10 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
                final double d11 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
                final int j = MathHelper.floor_double(d6 - d10 / 2.0D);
                final int k = MathHelper.floor_double(d7 - d11 / 2.0D);
                final int l = MathHelper.floor_double(d8 - d10 / 2.0D);
                final int i1 = MathHelper.floor_double(d6 + d10 / 2.0D);
                final int j1 = MathHelper.floor_double(d7 + d11 / 2.0D);
                final int k1 = MathHelper.floor_double(d8 + d10 / 2.0D);

                for (int blockX = j; blockX <= i1; ++blockX) {

                    final double d12 = ((double)blockX + 0.5D - d6) / (d10 / 2.0D);

                    if (d12 * d12 < 1.0D) {
                        for (int blockY = k; blockY <= j1; ++blockY) {

                            final double d13 = ((double)blockY + 0.5D - d7) / (d11 / 2.0D);

                            if (d12 * d12 + d13 * d13 < 1.0D) {
                                for (int blockZ = l; blockZ <= k1; ++blockZ) {

                                    final double d14 = ((double)blockZ + 0.5D - d8) / (d10 / 2.0D);

                                    if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {

                                        BlockPos position = new BlockPos(blockX, blockY, blockZ);
                                        IBlockState currentState = world.getBlockState(position);

                                        if (currentState.getBlock().isReplaceableOreGen(currentState, world, position, this._replaceCheck))
                                            world.setBlockState(position, this._oreBlock, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        private final IBlockState _oreBlock;
        private final Predicate<IBlockState> _replaceCheck;
        private final int _minBlocksCount;
        private final int _maxBlocksCount;
        private final int _minY;
        private final int _maxY;
        private final int _iterations;
    }
}
