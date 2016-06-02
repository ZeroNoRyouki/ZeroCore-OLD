package zero.mods.zerocore.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldHelper {

    public static boolean isEntityInRange(Entity entity, double x, double y, double z, double range) {

        return entity.getDistanceSq(x + 0.5, y + 0.5, z + 0.5) < (range * range);
    }

    public static boolean isEntityInRange(Entity entity, BlockPos position, double range) {

        return entity.getDistanceSq(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5) < (range * range);
    }


    public static void spawnVanillaParticles(World world, EnumParticleTypes particle, int minCount, int maxCount, int x, int y, int z, int offsetX, int offsetY, int offsetZ) {

        if (null == world)
            return;

        Random rand = world.rand;
        int howMany = MathHelper.getRandomIntegerInRange(rand, minCount, maxCount);
        double motionX, motionY, motionZ, pX, pY, pZ, px1, px2, py1, py2, pz1, pz2;

        px1 = x - offsetX + 0.5D;
        px2 = x + offsetX + 0.5D;
        py1 = y;
        py2 = y + offsetY;
        pz1 = z - offsetZ + 0.5D;
        pz2 = z + offsetZ + 0.5D;

        if (world instanceof WorldServer) {

            WorldServer ws = (WorldServer)world;

            motionX = rand.nextGaussian() * 0.02D;
            motionY = rand.nextGaussian() * 0.02D;
            motionZ = rand.nextGaussian() * 0.02D;

            pX = MathHelper.getRandomDoubleInRange(rand, px1, px2);
            pY = MathHelper.getRandomDoubleInRange(rand, py1, py2);
            pZ = MathHelper.getRandomDoubleInRange(rand, pz1, pz2);

            ws.spawnParticle(particle, pX, pY, pZ, howMany, motionX, motionY, motionZ, rand.nextGaussian() * 0.02D, EMPTY_INT_ARRAY);

        } else
            for (int i = 0; i < howMany; ++i) {

                motionX = rand.nextGaussian() * 0.02D;
                motionY = rand.nextGaussian() * 0.02D;
                motionZ = rand.nextGaussian() * 0.02D;

                pX = MathHelper.getRandomDoubleInRange(rand, px1, px2);
                pY = MathHelper.getRandomDoubleInRange(rand, py1, py2);
                pZ = MathHelper.getRandomDoubleInRange(rand, pz1, pz2);

                world.spawnParticle(particle, pX, pY, pZ, motionX, motionY, motionZ, EMPTY_INT_ARRAY);
            }
    }

    public static int getChunkXFromBlock(int blockX) {

        return blockX >> 4;
    }

    public static int getChunkXFromBlock(BlockPos position) {

        return position.getX() >> 4;
    }

    public static int getChunkZFromBlock(int blockZ) {

        return blockZ >> 4;
    }

    public static int getChunkZFromBlock(BlockPos position) {

        return position.getZ() >> 4;
    }

    public static long getChunkXZHashFromBlock(int blockX, int blockZ) {

        return ChunkCoordIntPair.chunkXZ2Int(WorldHelper.getChunkXFromBlock(blockX), WorldHelper.getChunkZFromBlock(blockZ));
    }

    public static long getChunkXZHashFromBlock(BlockPos position) {

        return ChunkCoordIntPair.chunkXZ2Int(WorldHelper.getChunkXFromBlock(position), WorldHelper.getChunkZFromBlock(position));
    }

    public static boolean blockChunkExists(IChunkProvider chunkProvider, BlockPos position) {

        return null != chunkProvider.getLoadedChunk(WorldHelper.getChunkXFromBlock(position), WorldHelper.getChunkZFromBlock(position));
   }




    private WorldHelper() {
    }


    public static final int[] EMPTY_INT_ARRAY = new int[0];

}
