package it.zerono.mods.zerocore.lib;

public enum MetalSize {

    Block("block"),
    Ingot("ingot"),
    Nugget("nugget"),
    Dust("dust");

    MetalSize(String orePrefix) {

        this.oreDictionaryPrefix = orePrefix;
    }

    public final String oreDictionaryPrefix;
}
