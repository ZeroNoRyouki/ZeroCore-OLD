package it.zerono.mods.zerocore.lib.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ModTileEntityMessageHandler<MessageT extends ModTileEntityMessage> extends ModMessageHandler<MessageT> {

    @Override
    protected void processMessage(final MessageT message, final MessageContext ctx) {

        final World world = this.getWorld(ctx);
        final BlockPos position = message.getPos();

        if (null == world) {

            FMLLog.severe("Invalid world instance found while processing a ModTileEntityMessage: skipping message");
            return;
        }

        if (null == position) {

            FMLLog.severe("Invalid tile entity position in a ModTileEntityMessage: skipping message");
            return;
        }

        final TileEntity tileEntity = world.getTileEntity(position);

        if (null != tileEntity) {

            this.processTileEntityMessage(message, ctx, tileEntity);
            return;
        }

        FMLLog.severe("Invalid tile entity found at %d, %d, %d while processing a ModTileEntityMessage: skipping message",
                position.getX(), position.getY(), position.getZ());
    }

    protected abstract World getWorld(final MessageContext ctx);

    protected abstract void processTileEntityMessage(final MessageT message, final MessageContext ctx, final TileEntity tileEntity);
}
