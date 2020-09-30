package org.jglrxavpok.redstonepixels;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RedstonePixels.MODID)
public class RedstonePixels
{
    public static final String MODID = "redstonepixels";
    public static final PixelBlock PIXEL_BLOCK = new PixelBlock();

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public RedstonePixels() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(PIXEL_BLOCK);
        }

        @SubscribeEvent
        public static void onItemRegistry(RegistryEvent.Register<Item> event) {
            Item item = new BlockItem(PIXEL_BLOCK, new Item.Properties().group(ItemGroup.REDSTONE))
                    .setRegistryName(PIXEL_BLOCK.getRegistryName());
            event.getRegistry().register(item);
        }

        @SubscribeEvent
        public static void onBlockColoring(ColorHandlerEvent.Block event) {
            event.getBlockColors().register((state, pos, world, _0) -> {
                int power = state.get(PixelBlock.POWER);
                DyeColor color = DyeColor.values()[15-power];
                return color.getColorValue();
            }, PIXEL_BLOCK);
        }
    }
}
