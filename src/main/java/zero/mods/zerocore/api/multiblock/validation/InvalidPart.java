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

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InvalidPart extends ValidationError {

    public InvalidPart(String messageResourceKey, int x, int y, int z) {

        super(messageResourceKey);
        this._x = x;
        this._y = y;
        this._z = z;
    }

    public InvalidPart(String messageResourceKey, BlockPos position) {

        super(messageResourceKey);
        this._x = position.getX();
        this._y = position.getY();
        this._z = position.getZ();
    }

    @Override
    public ITextComponent getChatMessage() {

        return new TextComponentTranslation(this._messageKey, this._x, this._y, this._z);
    }

    protected int _x;
    protected int _y;
    protected int _z;
}
