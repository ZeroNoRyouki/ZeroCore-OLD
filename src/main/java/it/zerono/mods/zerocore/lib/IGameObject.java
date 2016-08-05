package it.zerono.mods.zerocore.lib;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGameObject {

    /**
     * Called just after the object is registered in the GameRegistry
     */
    void onPostRegister();

    /**
     * Called on the client side just after the object is registered in the GameRegistry and onPostRegister()
     */
    @SideOnly(Side.CLIENT)
    void onPostClientRegister();

    /**
     * Register any entry for this object the Ore Dictionary
     */
    void registerOreDictionaryEntries();

    /**
     * Register any recipe for this object
     */
    void registerRecipes();
}
