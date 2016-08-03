package it.zerono.mods.zerocore.lib.config;

public interface IConfigListener {

    /**
     * Notify a listener that if should re-acquire it's own configuration options from the mod configuration handler
     */
    void onConfigChanged();
}
