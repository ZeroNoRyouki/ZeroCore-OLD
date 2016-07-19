package zero.mods.zerocore.internal.common;

import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import zero.mods.zerocore.api.multiblock.MultiblockRegistry;

public class MultiblockCommonHandler {

    public MultiblockCommonHandler() {
        FMLLog.bigWarning("MultiblockCommonHandler created!");
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onChunkLoad(ChunkEvent.Load loadEvent) {

        Chunk chunk = loadEvent.getChunk();

        MultiblockRegistry.onChunkLoaded(loadEvent.getWorld(), chunk.xPosition, chunk.zPosition);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onWorldUnload(WorldEvent.Unload unloadWorldEvent) {
        MultiblockRegistry.onWorldUnloaded(unloadWorldEvent.getWorld());
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {

        if (TickEvent.Phase.START == event.phase)
            MultiblockRegistry.tickStart(event.world);
    }
}
