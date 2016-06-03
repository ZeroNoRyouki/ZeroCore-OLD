package zero.mods.zerocore.api.multiblock.validation;

/*
 * A multiblock library for making irregularly-shaped multiblock machines
 *
 * Original author: Erogenous Beef
 * https://github.com/erogenousbeef/BeefCore
 *
 * Ported to Minecraft 1.9 by ZeroNoRyouki
 * https://github.com/ZeroNoRyouki/ZeroCore
 */

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InvalidMachineSize extends ValidationError {

    public InvalidMachineSize(String messageResourceKey, int correctSize, String dimension) {

        super(messageResourceKey);
        this._correctSize = correctSize;
        this._dimension = dimension;
    }

    @Override
    public ITextComponent getChatMessage() {

        return new TextComponentTranslation(this._messageKey, this._correctSize, this._dimension);
    }

    protected String _dimension;
    protected int _correctSize;
}
