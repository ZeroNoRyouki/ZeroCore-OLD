package it.zerono.mods.zerocore.internal.common.init;

import it.zerono.mods.zerocore.internal.ZeroCore;
import it.zerono.mods.zerocore.internal.common.item.ItemDebugTool;

public final class ZeroItems {

    public static final ItemDebugTool debugTool;

    public static void initialize() {
        // Just touch me ...
    }

    static {
        debugTool = ZeroCore.getProxy().register(new ItemDebugTool("debugTool"));
    }
}
