package zero.mods.zerocore.lib.block.properties;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class Orientation {
    /**
     * Common block-state properties for block orientation
     */
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyDirection HFACING = PropertyDirection.create("hfacing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyDirection VFACING = PropertyDirection.create("vfacing", EnumFacing.Plane.VERTICAL);

    /**
     * Return the suggested facing for a block indirectly placed in the world (by World.setBlockState for example)
     *
     * @param world the current world
     * @param position position of the block
     * @param currentFacing the current facing
     * @return the new facing for the block based on the surrounding blocks
     */
    public static EnumFacing suggestDefaultHorizontalFacing(World world, BlockPos position, EnumFacing currentFacing) {

        EnumFacing oppositeFacing = currentFacing.getOpposite();
        IBlockState facingState = world.getBlockState(position.offset(currentFacing));
        IBlockState oppositeState = world.getBlockState(position.offset(oppositeFacing));

        Block facingBlock = facingState.getBlock();
        Block oppositeBlock = oppositeState.getBlock();

        return facingBlock.isFullBlock(facingState) && !oppositeBlock.isFullBlock(oppositeState) ? oppositeFacing : currentFacing;
    }
}
