package it.zerono.mods.zerocore.util;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public final class CodeHelper {

    /**
     * Retrieve the ID of the mod from FML active mod container
     * Only call this method while processing a FMLEvent (or derived classes)
     */
    public static String getModIdFromActiveModContainer() {

        ModContainer mc = Loader.instance().activeModContainer();
        String modId = null != mc ? mc.getModId() : null;

        if ((null == modId) || modId.isEmpty())
            throw new RuntimeException("Cannot retrieve the MOD ID from FML");

        return modId;
    }

    /**
     * i18n support and helpers
     */

    public static String i18nValue(boolean value) {
        return I18n.format(value ? "debug.zerocore.true" : "debug.zerocore.false");
    }

}
