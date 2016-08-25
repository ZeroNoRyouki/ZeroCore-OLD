package it.zerono.mods.zerocore.lib;

import net.minecraft.util.text.ITextComponent;

public interface IDebugMessages {

    void add(ITextComponent message);

    void add(String messageFormatStringResourceKey, Object... messageParameters);
}
