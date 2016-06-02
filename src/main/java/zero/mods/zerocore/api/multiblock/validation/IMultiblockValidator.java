package zero.mods.zerocore.api.multiblock.validation;

public interface IMultiblockValidator {

    ValidationError getLastError();
    void setLastError(ValidationError error);
}
