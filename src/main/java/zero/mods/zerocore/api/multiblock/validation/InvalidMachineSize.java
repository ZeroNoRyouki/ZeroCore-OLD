package zero.mods.zerocore.api.multiblock.validation;

import net.minecraft.util.text.translation.I18n;

public class InvalidMachineSize extends ValidationError {

    public InvalidMachineSize(String messageResourceKey, int correctSize, char dimension) {

        super(messageResourceKey);
        this._correctSize = correctSize;
        this._dimension = dimension;
    }

    @Override
    public String getValidationMessage() {

        return I18n.translateToLocalFormatted(this._messageKey, this._correctSize, this._dimension);
    }

    protected char _dimension;
    protected int _correctSize;
}
