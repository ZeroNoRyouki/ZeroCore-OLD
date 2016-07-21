package it.zerono.mods.zerocore.lib.fluid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public abstract class ModFluid extends Fluid {

    public ModFluid(String fluidName, ResourceLocation still, ResourceLocation flowing) {

        super(fluidName, still, flowing);
        this.initialize();
    }

    protected abstract void initialize();
}
