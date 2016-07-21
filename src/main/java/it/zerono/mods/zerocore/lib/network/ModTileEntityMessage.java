package it.zerono.mods.zerocore.lib.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class ModTileEntityMessage implements IMessage {

    protected ModTileEntityMessage() {
        this._tilePosition = null;
    }

    protected ModTileEntityMessage(final BlockPos position) {
        this._tilePosition = position;
    }

    protected ModTileEntityMessage(final TileEntity tileEntity) {
        this._tilePosition = tileEntity.getPos();
    }

    public BlockPos getPos() {
        return this._tilePosition;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {

        int x = buffer.readInt(),
            y = buffer.readInt(),
            z = buffer.readInt();

        this._tilePosition = new BlockPos(x, y, z);
    }

    @Override
    public void toBytes(ByteBuf buffer) {

        buffer.writeInt(this._tilePosition.getX());
        buffer.writeInt(this._tilePosition.getY());
        buffer.writeInt(this._tilePosition.getZ());
    }

    private BlockPos _tilePosition;
}
