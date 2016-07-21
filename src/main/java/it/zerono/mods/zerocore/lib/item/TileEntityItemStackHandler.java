package it.zerono.mods.zerocore.lib.item;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityItemStackHandler extends ItemStackHandler {

    public TileEntityItemStackHandler(TileEntity linkedTileEntity) {
        super(1);
        this._linkedTE = linkedTileEntity;
    }

    public TileEntityItemStackHandler(TileEntity linkedTileEntity, int size) {
        super(size);
        this._linkedTE = linkedTileEntity;
    }

    public TileEntityItemStackHandler(TileEntity linkedTileEntity, ItemStack[] stacks) {
        super(stacks);
        this._linkedTE = linkedTileEntity;
    }

    @Override
    protected void onContentsChanged(int slot) {

        this._linkedTE.markDirty();
    }

    private TileEntity _linkedTE;
}
