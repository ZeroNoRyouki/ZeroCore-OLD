package zero.mods.zerocore.lib.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * A base class for modded tile entities
 *
 * Partially ported from TileCoFHBase
 * https://github.com/CoFH/CoFHCore/blob/master/src/main/java/cofh/core/block/TileCoFHBase.java
 */
public abstract class ModTileEntity extends TileEntity {
    
    /*
    GUI management
     */

    /**
     * Check if the tile entity has a GUI or not
     * Override in derived classes to return true if your tile entity got a GUI
     */
    public boolean canOpenGui() {

        return false;
    }

    /**
     * Open the default GUI of this tile entity
     * Use along with ModGuiHandler
     *
     * @param player the player currently interacting with your block/tile entity
     * @return true if the GUI was opened, false otherwise
     */
    public boolean openGui(Object mod, EntityPlayer player) {

        return this.openGui(mod, player, -1);
    }

    /**
     * Open the specified GUI
     *
     * @param player the player currently interacting with your block/tile entity
     * @param guiId the GUI to open
     * @return true if the GUI was opened, false otherwise
     */
    public boolean openGui(Object mod, EntityPlayer player, int guiId) {

        if (this.canOpenGui()) {

            player.openGui(mod, guiId, this.worldObj, this.pos.getX(), this.pos.getY(), this.pos.getZ());
            return true;
        }

        return false;
    }

    /**
     * Returns a Server side Container to be displayed to the user.
     *
     * @param playerInventory The inventory of the player
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    public Object getServerGuiElement(InventoryPlayer playerInventory) {

        return null;
    }

    /**
     * Returns a Container to be displayed to the user. On the client side, this
     * needs to return a instance of GuiScreen On the server side, this needs to
     * return a instance of Container
     *
     * @param playerInventory The inventory of the player
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    public Object getClientGuiElement(InventoryPlayer playerInventory) {

        return null;
    }

    /*
    Updated packed handling
     */

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        this.loadFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {

        super.writeToNBT(nbt);
        this.saveToNBT(nbt);
    }

    @Override
    public Packet<?> getDescriptionPacket() {

        NBTTagCompound nbt = new NBTTagCompound();

        this.saveToNBT(nbt);

        return new SPacketUpdateTileEntity(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {

        super.onDataPacket(net, packet);

        NBTTagCompound nbt = packet.getNbtCompound();

        if (null != nbt)
            this.loadFromNBT(nbt);
    }

    protected abstract void loadFromNBT(NBTTagCompound nbt);

    protected abstract void saveToNBT(NBTTagCompound nbt);


    /*
     Chunk and block updates
     */

    @Override
    public void onChunkUnload() {

        if (!tileEntityInvalid) {
            invalidate(); // this isn't called when a tile unloads. guard incase it is in the future
        }
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
}
