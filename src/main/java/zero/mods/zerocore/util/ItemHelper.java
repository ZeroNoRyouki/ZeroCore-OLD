package zero.mods.zerocore.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class ItemHelper {

    public static ItemStack createItemStack(IBlockState state, int amount) {

        Block block = state.getBlock();
        Item item = Item.getItemFromBlock(block);

        if (null != item) {

            int meta = item.getHasSubtypes() ? block.getMetaFromState(state) : 0;

            return new ItemStack(item, amount, meta);
        }

        return null;
    }

    private ItemHelper() {
    }
}
