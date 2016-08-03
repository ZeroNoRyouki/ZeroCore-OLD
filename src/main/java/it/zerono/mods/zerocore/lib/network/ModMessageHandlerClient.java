package it.zerono.mods.zerocore.lib.network;

import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ModMessageHandlerClient<MessageT extends IMessage> extends ModMessageHandler<MessageT> {

    @SideOnly(Side.CLIENT)
    @Override
    protected IThreadListener getThreadListener(MessageContext ctx) {
        return FMLClientHandler.instance().getClient();
    }
}
