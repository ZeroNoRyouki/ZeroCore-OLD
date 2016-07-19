package zero.mods.zerocore.internal.common;

import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void initMultiblockRegistry() {

        if (null == s_multiblockHandler)
            MinecraftForge.EVENT_BUS.register(s_multiblockHandler = new MultiblockCommonHandler());
    }

    private static MultiblockCommonHandler s_multiblockHandler = null;
}
