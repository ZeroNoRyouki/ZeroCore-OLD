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

public interface IMultiblockValidator {

    ValidationError getLastError();
    void setLastError(ValidationError error);
}
