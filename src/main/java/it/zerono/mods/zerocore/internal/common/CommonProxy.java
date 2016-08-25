package it.zerono.mods.zerocore.internal.common;

import it.zerono.mods.zerocore.api.multiblock.IMultiblockRegistry;
import it.zerono.mods.zerocore.lib.IGameObject;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

    public <T extends Item & IGameObject> T register(T item) {

        GameRegistry.register(item);
        item.onPostRegister();
        return item;
    }

    public IMultiblockRegistry initMultiblockRegistry() {

        if (null == s_multiblockHandler)
            MinecraftForge.EVENT_BUS.register(s_multiblockHandler = new MultiblockEventHandler());

        return MultiblockRegistry.INSTANCE;
    }

    private static MultiblockEventHandler s_multiblockHandler = null;
}
