package it.zerono.mods.zerocore.lib.network;

import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class ModMessageHandler<MessageT extends IMessage> implements IMessageHandler<MessageT, IMessage> {

    public IMessage onMessage(final MessageT message, final MessageContext ctx) {

        final ModMessageHandler<MessageT> handler = this;
        final IThreadListener listener = this.getThreadListener(ctx);

        listener.addScheduledTask(new Runnable() {
            public void run() {
                handler.processMessage(message, ctx);
            }
        });

        return null;
    }

    protected abstract IThreadListener getThreadListener(final MessageContext ctx);

    protected abstract void processMessage(final MessageT message, final MessageContext ctx);
}
