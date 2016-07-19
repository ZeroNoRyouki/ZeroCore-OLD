package zero.mods.zerocore.lib.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import java.util.List;

public interface ICustomModelsProvider {

    List<Pair<Integer, ModelResourceLocation>> getMetadataToModelMappings();
}
