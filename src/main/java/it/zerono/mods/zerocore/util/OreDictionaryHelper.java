package it.zerono.mods.zerocore.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class OreDictionaryHelper {

    public static String[] getOreNames(ItemStack stack) {

        String[] names;
        int[] oreIDs = OreDictionary.getOreIDs(stack);
        int count;

        if (oreIDs == null || (count = oreIDs.length) < 1)
            return null;

        names = new String[count];

        for (int i = 0; i < count; ++i)
            names[i] = OreDictionary.getOreName(oreIDs[i]);

        return names;
    }

    public static ItemStack getOre(String name) {

        return !OreDictionaryHelper.doesOreNameExist(name) ? null :
                OreDictionaryHelper.duplicateStack(OreDictionary.getOres(name).get(0), 1);
    }

    public static boolean doesOreNameExist(String name) {

        return OreDictionary.doesOreNameExist(name);
    }

    private static ItemStack duplicateStack(ItemStack stack, int stackSize) {

        ItemStack dup = stack.copy();

        dup.stackSize = stackSize;
        return dup;
    }


    private OreDictionaryHelper() {
    }
}
