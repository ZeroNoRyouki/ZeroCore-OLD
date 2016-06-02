package zero.mods.zerocore.api.multiblock.validation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

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
    public String getValidationMessage() {

        return I18n.translateToLocalFormatted(this._messageKey, this._x, this._y, this._z);
    }

    protected int _x;
    protected int _y;
    protected int _z;
}
