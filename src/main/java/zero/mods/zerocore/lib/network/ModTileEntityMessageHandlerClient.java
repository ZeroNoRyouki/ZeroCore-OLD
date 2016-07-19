package zero.mods.zerocore.lib.network;

import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ModTileEntityMessageHandlerClient<MessageT extends ModTileEntityMessage>
        extends ModTileEntityMessageHandler<MessageT> {

    @Override
    protected IThreadListener getThreadListener(MessageContext ctx) {
        return FMLClientHandler.instance().getClient();
    }

    @Override
    protected World getWorld(MessageContext ctx) {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
