package it.zerono.mods.zerocore.internal;

import it.zerono.mods.zerocore.internal.common.CommonProxy;
import it.zerono.mods.zerocore.internal.common.init.ZeroItems;
import it.zerono.mods.zerocore.lib.IModInitializationHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, acceptedMinecraftVersions = References.MOD_MCVERSION,
        dependencies = References.MOD_DEPENDENCIES, version = References.MOD_VERSION)
public final class ZeroCore implements IModInitializationHandler {

    public static CommonProxy getProxy() {
        return s_proxy;
    }

    @Override
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        ZeroItems.initialize();
    }

    @Override
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        ZeroItems.debugTool.registerRecipes();
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        // nothing to do here so no @Mod.EventHandler annotation
    }

    @Mod.Instance
    private static ZeroCore s_instance;

    @SidedProxy(clientSide = References.PROXY_CLIENT, serverSide = References.PROXY_COMMON)
    private static CommonProxy s_proxy;
}
