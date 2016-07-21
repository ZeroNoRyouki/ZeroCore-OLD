package it.zerono.mods.zerocore.lib.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import it.zerono.mods.zerocore.lib.block.ModTileEntity;

public class ModGuiHandler implements IGuiHandler {

    /**
     * Create a new instance of the GUI handler and register it in the game
     *
     * @param modInstance the calling mod instance
     */
    public ModGuiHandler(Object modInstance) {

        NetworkRegistry.INSTANCE.registerGuiHandler(modInstance, this);
    }

    @Override
    public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        return (te instanceof ModTileEntity) ? ((ModTileEntity)te).getServerGuiElement(guiId, player) : null;
    }

    @Override
    public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

        return (te instanceof ModTileEntity) ? ((ModTileEntity)te).getClientGuiElement(guiId, player) : null;
    }

}
