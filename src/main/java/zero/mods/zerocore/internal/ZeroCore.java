package zero.mods.zerocore.internal;

import net.minecraftforge.fml.common.Mod;

@Mod(modid = References.MOD_ID, acceptedMinecraftVersions = References.MOD_MCVERSION,
        dependencies = References.MOD_DEPENDENCIES, version = References.MOD_VERSION)
public class ZeroCore {
    
    @Mod.Instance
    private static ZeroCore s_instance;
}
