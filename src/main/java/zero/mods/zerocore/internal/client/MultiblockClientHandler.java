package zero.mods.zerocore.internal.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zero.mods.zerocore.api.multiblock.MultiblockRegistry;

public class MultiblockClientHandler {

    public MultiblockClientHandler() {
        FMLLog.bigWarning("MultiblockClientHandler created!");
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {

        if (TickEvent.Phase.START == event.phase)
            MultiblockRegistry.tickStart(Minecraft.getMinecraft().theWorld);
    }
}
