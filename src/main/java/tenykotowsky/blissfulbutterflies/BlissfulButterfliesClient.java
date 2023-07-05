package tenykotowsky.blissfulbutterflies;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import tenykotowsky.blissfulbutterflies.entity.ModEntities;
import tenykotowsky.blissfulbutterflies.entity.client.ButterflyRenderer;

public class BlissfulButterfliesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.BUTTERFLY, ButterflyRenderer::new);

    }
}
