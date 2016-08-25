package it.zerono.mods.zerocore.internal.client;

import it.zerono.mods.zerocore.internal.common.CommonProxy;
import it.zerono.mods.zerocore.lib.IGameObject;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy {

    @Override
    public <T extends Item & IGameObject> T register(T item) {

        super.register(item);
        item.onPostClientRegister();
        return item;
    }
}
