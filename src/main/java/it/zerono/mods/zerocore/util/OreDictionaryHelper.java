package it.zerono.mods.zerocore.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

public final class OreDictionaryHelper {

    public static String[] getOreNames(ItemStack stack) {

        if (null == stack)
            return null;

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

    public static String[] getOreNames(@Nonnull IBlockState state) {
        return OreDictionaryHelper.getOreNames(ItemHelper.createItemStack(state, 1));
    }

    public static String getFirstOreName(ItemStack stack) {

        String[] names = null != stack ? OreDictionaryHelper.getOreNames(stack) : null;

        return null != names && names.length > 0 ? names[0] : "";
    }

    public static String getFirstOreName(@Nonnull IBlockState state) {
        return OreDictionaryHelper.getFirstOreName(ItemHelper.createItemStack(state, 1));
    }

    /**
     * Get a copy of the first registered items to this ore type
     *
     * @param name the ore name
     * @return the first ItemStacks registered for this ore or null if the are no ItemStacks registered
     */
    public static ItemStack getOre(String name) {

        return !OreDictionaryHelper.doesOreNameExist(name) ? null :
                ItemHandlerHelper.copyStackWithSize(OreDictionary.getOres(name).get(0), 1);
    }

    public static boolean doesOreNameExist(String name) {

        return OreDictionary.doesOreNameExist(name);
    }

    private OreDictionaryHelper() {
    }
}
