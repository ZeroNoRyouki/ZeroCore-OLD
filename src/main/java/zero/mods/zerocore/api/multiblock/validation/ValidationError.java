package zero.mods.zerocore.api.multiblock.validation;


import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

public class ValidationError {

    public static final ValidationError VALIDATION_SUCCESS = new ValidationError("zerocore:api.multiblock.validation.success");
    public static final ValidationError VALIDATION_ERROR_TOO_FEW_PARTS = new ValidationError("zerocore:api.multiblock.validation.too_few_parts");

    public ValidationError(String messageResourceKey) {

        this._messageKey = messageResourceKey;
    }

    public String getValidationMessage() {

        return I18n.translateToLocal(this._messageKey);
    }

    protected String _messageKey;






}
