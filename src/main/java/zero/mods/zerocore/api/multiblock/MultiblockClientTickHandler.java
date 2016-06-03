package zero.mods.zerocore.api.multiblock;

/*
 * A multiblock library for making irregularly-shaped multiblock machines
 *
 * Original author: Erogenous Beef
 * https://github.com/erogenousbeef/BeefCore
 *
 * Ported to Minecraft 1.9 by ZeroNoRyouki
 * https://github.com/ZeroNoRyouki/ZeroCore
 */

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MultiblockClientTickHandler {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) {
            MultiblockRegistry.tickStart(Minecraft.getMinecraft().theWorld);
        }
    }
}
