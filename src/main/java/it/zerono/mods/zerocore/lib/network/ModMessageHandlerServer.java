package it.zerono.mods.zerocore.lib.network;

import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ModMessageHandlerServer<MessageT extends IMessage> extends ModMessageHandler<MessageT> {

    @Override
    protected IThreadListener getThreadListener(MessageContext ctx) {
        return (WorldServer)ctx.getServerHandler().playerEntity.worldObj;
    }
}
