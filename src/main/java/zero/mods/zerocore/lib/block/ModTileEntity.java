package zero.mods.zerocore.lib.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zero.mods.zerocore.lib.network.ModTileEntityMessage;
import zero.mods.zerocore.util.WorldHelper;

/**
 * A base class for modded tile entities
 *
 * Partially ported from TileCoFHBase
 * https://github.com/CoFH/CoFHCore/blob/master/src/main/java/cofh/core/block/TileCoFHBase.java
 */
public abstract class ModTileEntity extends TileEntity {

    public boolean isUseableByPlayer(EntityPlayer entityplayer) {

        BlockPos position = this.getPos();

        if (worldObj.getTileEntity(position) != this)
            return false;

        return entityplayer.getDistanceSq((double)position.getX() + 0.5D, (double)position.getY() + 0.5D,
                (double)position.getZ() + 0.5D) <= 64D;
    }

    /*
    GUI management
     */

    /**
     * Check if the tile entity has a GUI or not
     * Override in derived classes to return true if your tile entity got a GUI
     */
    public boolean canOpenGui(World world, BlockPos posistion, IBlockState state) {
        return false;
    }

    /**
     * Open the specified GUI
     *
     * @param player the player currently interacting with your block/tile entity
     * @param guiId the GUI to open
     * @return true if the GUI was opened, false otherwise
     */
    public boolean openGui(Object mod, EntityPlayer player, int guiId) {

        player.openGui(mod, guiId, this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ());
        return true;
    }

    /**
     * Returns a Server side Container to be displayed to the user.
     *
     * @param guiId the GUI ID mumber
     * @param player the player currently interacting with your block/tile entity
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    public Object getServerGuiElement(int guiId, EntityPlayer player) {
        return null;
    }

    /**
     * Returns a Container to be displayed to the user. On the client side, this
     * needs to return a instance of GuiScreen On the server side, this needs to
     * return a instance of Container
     *
     * @param guiId the GUI ID mumber
     * @param player the player currently interacting with your block/tile entity
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    public Object getClientGuiElement(int guiId, EntityPlayer player) {
        return null;
    }

    /*
    Updated packed handling
     */

    @Override
    public final void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        this.loadFromNBT(nbt, false);
    }

    @Override
    public final void writeToNBT(NBTTagCompound nbt) {

        super.writeToNBT(nbt);
        this.saveToNBT(nbt, false);
    }

    @Override
    public final Packet<?> getDescriptionPacket() {

        NBTTagCompound nbt = new NBTTagCompound();

        this.saveToNBT(nbt, true);

        return new SPacketUpdateTileEntity(this.pos, 0, nbt);
    }

    /**
     * Called when you receive a TileEntityData packet for the location this
     * TileEntity is currently in. On the client, the NetworkManager will always
     * be the remote server. On the server, it will be whomever is responsible for
     * sending the packet.
     *
     * @param net The NetworkManager the packet originated from
     * @param packet The data packet
     */
    @Override
    public final void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {

        super.onDataPacket(net, packet);

        NBTTagCompound nbt = packet.getNbtCompound();

        if (null != nbt)
            this.loadFromNBT(nbt, true);
    }

    protected abstract void loadFromNBT(NBTTagCompound nbt, boolean fromPacket);

    protected abstract void saveToNBT(NBTTagCompound nbt);


    /*
     Chunk and block updates
     */

    @Override
    public void onChunkUnload() {

        if (!tileEntityInvalid)
            this.invalidate();
    }

    public void markChunkDirty() {

        this.worldObj.markChunkDirty(this.getPos(), this);
    }

    public void callNeighborBlockChange() {

        this.worldObj.notifyNeighborsOfStateChange(this.getPos(), this.getBlockType());
    }

    @Deprecated // not implemented
    public void callNeighborTileChange() {

        //this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
    }

    public void notifyBlockUpdate() {

        WorldHelper.notifyBlockUpdate(this.worldObj, this.getPos(), null, null);
    }

    public void notifyBlockUpdate(IBlockState oldState, IBlockState newState) {

        WorldHelper.notifyBlockUpdate(this.worldObj, this.getPos(), oldState, newState);
    }

    public void nofityTileEntityUpdate() {

        this.markDirty();
        WorldHelper.notifyBlockUpdate(this.worldObj, this.getPos(), null, null);
    }
}
