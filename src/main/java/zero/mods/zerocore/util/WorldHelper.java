package zero.mods.zerocore.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class WorldHelper {

    /**
     * Test if we were called by the Server thread or by another thread in a server environment
     *
     * @param world A valid world instance
     */
    public static boolean calledByLogicalServer(World world) {

        return !world.isRemote;
    }

    /**
     * Test if we were called by the Client thread or by another thread in a client-only or combined environment
     *
     * @param world A valid world instance
     */
    public static boolean calledByLogicalClient(World world) {

        return world.isRemote;
    }

    public static boolean isEntityInRange(Entity entity, double x, double y, double z, double range) {

        return entity.getDistanceSq(x + 0.5, y + 0.5, z + 0.5) < (range * range);
    }

    public static boolean isEntityInRange(Entity entity, BlockPos position, double range) {

        return entity.getDistanceSq(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5) < (range * range);
    }


    public static void spawnVanillaParticles(World world, EnumParticleTypes particle, int minCount, int maxCount,
                                             int x, int y, int z, int offsetX, int offsetY, int offsetZ) {

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

    /**
     * Spawn an ItemStack in the world
     * This replace StacksHelper.spawnInWorld
     *
     * @param stack the stack
     * @param world the world to spawn in
     * @param x spawn coordinates
     * @param y spawn coordinates
     * @param z spawn coordinates
     * @param withMomentum if true, add momentum to the stack
     */
    public static void spawnItemStack(ItemStack stack, World world, double x, double y, double z, boolean withMomentum) {

        float x2, y2, z2;

        if (withMomentum) {
            x2 = world.rand.nextFloat() * 0.8F + 0.1F;
            y2 = world.rand.nextFloat() * 0.8F + 0.1F;
            z2 = world.rand.nextFloat() * 0.8F + 0.1F;
        } else {
            x2 = 0.5F;
            y2 = 0.0F;
            z2 = 0.5F;
        }

        EntityItem entity = new EntityItem(world, x + x2, y + y2, z + z2, stack.copy());

        if (withMomentum) {
            entity.motionX = (float) world.rand.nextGaussian() * 0.05F;
            entity.motionY = (float) world.rand.nextGaussian() * 0.05F + 0.2F;
            entity.motionZ = (float) world.rand.nextGaussian() * 0.05F;
        } else {
            entity.motionY = -0.05F;
            entity.motionX = 0;
            entity.motionZ = 0;
        }

        world.spawnEntityInWorld(entity);
    }

    /*
    Chunk and block update helpers
     */

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

    @Deprecated // use World.isBlockLoaded instead
    public static boolean blockChunkExists(IChunkProvider chunkProvider, BlockPos position) {

        return null != chunkProvider.getLoadedChunk(WorldHelper.getChunkXFromBlock(position), WorldHelper.getChunkZFromBlock(position));
    }

    /**
     * force a block update at the given position
     * @param world the world to update
     * @param position the position of the block begin updated
     * @param oldState the old state of the block begin updated. if null, the current state will be retrieved from the world
     * @param newState the new state for the block begin updated. if null, the final value of oldState will be used
     */
    public static void notifyBlockUpdate(World world, BlockPos position, IBlockState oldState, IBlockState newState) {

        if (null == oldState)
            oldState = world.getBlockState(position);

        if (null == newState)
            newState = oldState;

        world.notifyBlockUpdate(position, oldState, newState, 3);
    }

    private WorldHelper() {
    }

    public static final int[] EMPTY_INT_ARRAY = new int[0];
}
