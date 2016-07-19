package zero.mods.zerocore.lib.network;

import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ModTileEntityMessageHandlerServer<MessageT extends ModTileEntityMessage>
        extends ModTileEntityMessageHandler<MessageT> {

    @Override
    protected IThreadListener getThreadListener(MessageContext ctx) {
        return (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
    }

    @Override
    protected World getWorld(MessageContext ctx) {
        return ctx.getServerHandler().playerEntity.worldObj;
    }
}
