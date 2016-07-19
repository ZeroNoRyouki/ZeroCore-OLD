package zero.mods.zerocore.internal.client;

import net.minecraftforge.common.MinecraftForge;
import zero.mods.zerocore.internal.common.CommonProxy;

public class ClientProxy extends CommonProxy {

    public void initMultiblockRegistry() {

        super.initMultiblockRegistry();

        if (null == s_multiblockHandler)
            MinecraftForge.EVENT_BUS.register(s_multiblockHandler = new MultiblockClientHandler());
    }

    private static MultiblockClientHandler s_multiblockHandler = null;
}
