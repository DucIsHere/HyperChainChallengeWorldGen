package com.ducishere.hyperworldgen;

import com.ducishere.hyperworldgen.block.ModBlocks;
import com.ducishere.hyperworldgen.effect.ModEffects;
import com.ducishere.hyperworldgen.item.ModItems;
import com.ducishere.hyperworldgen.world.feature.ModConfiguredFeatures;
import com.ducishere.hyperworldgen.world.feature.ModPlacedFeatures;
import com.ducishere.hyperworldgen.handler.FrostbiteHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;
import com.ducishere.hyperworldgen.environment.ColdEnvironment;

import net.fabricmc.api.ModInitializer;

public class HyperWorldGen implements ModInitializer {

    public static final String MOD_ID = "hyperworldgen";

    @Override
    public void onInitialize() {
        System.out.println("Initializing HyperWorldGen...");

        ModBlocks.registerBlocks();

        ModItems.registerItems();
        SteelIngot.register();
        SteelReinforcedIngot.register();
        SteelAbyssIngot.register();
        GlacialIceShard.register();
        IceSteelReinforcedIngot.register();
        SteelArmorUpgrade.register();

        ModRecipes.registerRecipes();

        ModEffects.registerModEffects();

        ModConfiguredFeatures.bootstrap();
        ModPlacedFeatures.bootstrap();

        ModBiomes.registerBiomes();
        FrostbiteHandler.register();

        // Nếu cần truyền registry, truyền đúng args
        // ModConfiguredFeatures.bootstrap(registry);
        // ModPlacedFeatures.bootstrap(registry);

        ModWorldGen.generateWorldGen();

        HandledScreens.register(ModScreenHandlers.CRYO_FURNACE_SCREEN_HANDLER, CryoFurnaceScreen::new);

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if(world instanceof ServerWorld serverWorld){
                ColdEnvironment.tick(serverWorld);
            }
        });
    }
}
