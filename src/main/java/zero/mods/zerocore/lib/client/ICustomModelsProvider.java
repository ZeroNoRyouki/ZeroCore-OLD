package zero.mods.zerocore.lib.client;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import java.util.List;

public interface ICustomModelsProvider {

    ResourceLocation getCustomResourceLocation();

    List<Pair<Integer, String>> getMetadataToModelMappings();
}
