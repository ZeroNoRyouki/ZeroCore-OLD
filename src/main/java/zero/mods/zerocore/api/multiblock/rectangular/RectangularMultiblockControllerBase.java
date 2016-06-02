package zero.mods.zerocore.api.multiblock.rectangular;

/*
 * A multiblock library for making irregularly-shaped multiblock machines
 *
 * Original author: Erogenous Beef
 * https://github.com/erogenousbeef/BeefCore
 *
 * Ported to Minecraft 1.9 by ZeroNoRyouki
 * https://github.com/ZeroNoRyouki/ZeroCore
 */

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zero.mods.zerocore.api.multiblock.MultiblockControllerBase;
import zero.mods.zerocore.api.multiblock.MultiblockValidationException;

public abstract class RectangularMultiblockControllerBase extends
		MultiblockControllerBase {

	protected RectangularMultiblockControllerBase(World world) {
		super(world);
	}

	/**
	 * @return True if the machine is "whole" and should be assembled. False otherwise.
	 */
	protected void isMachineWhole() throws MultiblockValidationException {
		if(connectedParts.size() < getMinimumNumberOfBlocksForAssembledMachine()) {
			throw new MultiblockValidationException("Machine is too small.");
		}
		
		BlockPos maximumCoord = this.getMaximumCoord();
		BlockPos minimumCoord = this.getMinimumCoord();

		int minX = minimumCoord.getX();
		int minY = minimumCoord.getY();
		int minZ = minimumCoord.getZ();
		int maxX = maximumCoord.getX();
		int maxY = maximumCoord.getY();
		int maxZ = maximumCoord.getZ();
		
		// Quickly check for exceeded dimensions
		int deltaX = maxX - minX + 1;
		int deltaY = maxY - minY + 1;
		int deltaZ = maxZ - minZ + 1;
		
		int maxXSize = this.getMaximumXSize();
		int maxYSize = this.getMaximumYSize();
		int maxZSize = this.getMaximumZSize();
		int minXSize = this.getMinimumXSize();
		int minYSize = this.getMinimumYSize();
		int minZSize = this.getMinimumZSize();
		
		if (maxXSize > 0 && deltaX > maxXSize) { throw new MultiblockValidationException(String.format("Machine is too large, it may be at most %d blocks in the X dimension", maxXSize)); }
		if (maxYSize > 0 && deltaY > maxYSize) { throw new MultiblockValidationException(String.format("Machine is too large, it may be at most %d blocks in the Y dimension", maxYSize)); }
		if (maxZSize > 0 && deltaZ > maxZSize) { throw new MultiblockValidationException(String.format("Machine is too large, it may be at most %d blocks in the Z dimension", maxZSize)); }
		if (deltaX < minXSize) { throw new MultiblockValidationException(String.format("Machine is too small, it must be at least %d blocks in the X dimension", minXSize)); }
		if (deltaY < minYSize) { throw new MultiblockValidationException(String.format("Machine is too small, it must be at least %d blocks in the Y dimension", minYSize)); }
		if (deltaZ < minZSize) { throw new MultiblockValidationException(String.format("Machine is too small, it must be at least %d blocks in the Z dimension", minZSize)); }

		// Now we run a simple check on each block within that volume.
		// Any block deviating = NO DEAL SIR
		TileEntity te;
		RectangularMultiblockTileEntityBase part;
		Class<? extends RectangularMultiblockControllerBase> myClass = this.getClass();

		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				for(int z = minZ; z <= maxZ; z++) {
					// Okay, figure out what sort of block this should be.
					
					te = this.worldObj.getTileEntity(new BlockPos(x, y, z));
					if(te instanceof RectangularMultiblockTileEntityBase) {
						part = (RectangularMultiblockTileEntityBase)te;
						
						// Ensure this part should actually be allowed within a cube of this controller's type
						if(!myClass.equals(part.getMultiblockControllerType()))
						{
							throw new MultiblockValidationException(String.format("Part @ %d, %d, %d is incompatible with machines of type %s", x, y, z, myClass.getSimpleName()));
						}
					}
					else {
						// This is permitted so that we can incorporate certain non-multiblock parts inside interiors
						part = null;
					}
					
					// Validate block type against both part-level and material-level validators.
					int extremes = 0;
					if(x == minX) { extremes++; }
					if(y == minY) { extremes++; }
					if(z == minZ) { extremes++; }
					
					if(x == maxX) { extremes++; }
					if(y == maxY) { extremes++; }
					if(z == maxZ) { extremes++; }
					
					if(extremes >= 2) {
						if(part != null) {
							part.isGoodForFrame();
						}
						else {
							isBlockGoodForFrame(this.worldObj, x, y, z);
						}
					}
					else if(extremes == 1) {
						if(y == maxY) {
							if(part != null) {
								part.isGoodForTop();
							}
							else {
								isBlockGoodForTop(this.worldObj, x, y, z);
							}
						}
						else if(y == minY) {
							if(part != null) {
								part.isGoodForBottom();
							}
							else {
								isBlockGoodForBottom(this.worldObj, x, y, z);
							}
						}
						else {
							// Side
							if(part != null) {
								part.isGoodForSides();
							}
							else {
								isBlockGoodForSides(this.worldObj, x, y, z);
							}
						}
					}
					else {
						if(part != null) {
							part.isGoodForInterior();
						}
						else {
							isBlockGoodForInterior(this.worldObj, x, y, z);
						}
					}
				}
			}
		}
	}	
	
}
