package it.zerono.mods.zerocore.internal;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import it.zerono.mods.zerocore.internal.common.CommonProxy;

@Mod(modid = References.MOD_ID, acceptedMinecraftVersions = References.MOD_MCVERSION,
        dependencies = References.MOD_DEPENDENCIES, version = References.MOD_VERSION)
public final class ZeroCore {

    public static CommonProxy getProxy() {
        return s_proxy;
    }
    
    @Mod.Instance
    private static ZeroCore s_instance;

    @SidedProxy(clientSide = References.PROXY_CLIENT, serverSide = References.PROXY_COMMON)
    private static CommonProxy s_proxy;
}
