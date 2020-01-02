package WayofTime.bloodmagic.ritual.harvest;

import WayofTime.bloodmagic.util.BMLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Harvest handler for standard plantable crops such as Wheat, Potatoes, and
 * Netherwart. <br>
 * Register a new crop for this handler with {@link HarvestRegistry#registerStandardCrop(Block, int)}
 */
public class HarvestHandlerPlantable implements IHarvestHandler {

    public HarvestHandlerPlantable() {
        HarvestRegistry.registerStandardCrop(Blocks.CARROTS, 7);
        HarvestRegistry.registerStandardCrop(Blocks.WHEAT, 7);
        HarvestRegistry.registerStandardCrop(Blocks.POTATOES, 7);
        HarvestRegistry.registerStandardCrop(Blocks.BEETROOTS, 3);
        HarvestRegistry.registerStandardCrop(Blocks.NETHER_WART, 3);

        addThirdPartyCrop("actuallyadditions", "blockFlax", 7);
        addThirdPartyCrop("actuallyadditions", "blockCanola", 7);
        addThirdPartyCrop("actuallyadditions", "blockRice", 7);

        addThirdPartyCrop("extrautils2", "redorchid", 6);
        addThirdPartyCrop("extrautils2", "enderlily", 7);

        addThirdPartyCrop("roots", "moonglow", 7);
        addThirdPartyCrop("roots", "terra_moss", 7);
        addThirdPartyCrop("roots", "pereskia", 7);
        addThirdPartyCrop("roots", "wildroot", 7);
        addThirdPartyCrop("roots", "aubergine", 7);
        addThirdPartyCrop("roots", "spirit_herb", 7);

        addPamCrops();

        addMysCrops();
    }

    @Override
    public boolean harvest(World world, BlockPos pos, IBlockState state, List<ItemStack> drops) {
        NonNullList<ItemStack> blockDrops = NonNullList.create();
        state.getBlock().getDrops(blockDrops, world, pos, state, 0);
        boolean foundSeed = false;

        for (ItemStack stack : blockDrops) {
            if (stack.isEmpty())
                continue;

            if (stack.getItem() instanceof IPlantable) {
                stack.shrink(1);
                foundSeed = true;
                break;
            }
        }

        if (foundSeed) {
            world.setBlockState(pos, state.getBlock().getDefaultState());
            world.playEvent(2001, pos, Block.getStateId(state));
            for (ItemStack stack : blockDrops) {
                if (stack.isEmpty())
                    continue;

                drops.add(stack);
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean test(World world, BlockPos pos, IBlockState state) {
        return HarvestRegistry.getStandardCrops().containsKey(state.getBlock()) && state.getBlock().getMetaFromState(state) == HarvestRegistry.getStandardCrops().get(state.getBlock());
    }

    private static void addThirdPartyCrop(String modid, String regName, int matureMeta) {
        if (!Loader.isModLoaded(modid))
            return;

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modid, regName));
        if (block != null && block != Blocks.AIR)
            HarvestRegistry.registerStandardCrop(block, matureMeta);
    }

    private static void addPamCrops() {
        if (!Loader.isModLoaded("harvestcraft"))
            return;

        try {
            Class<?> pamRegistry = Class.forName("com.pam.harvestcraft.blocks.CropRegistry");
            Field names = pamRegistry.getDeclaredField("cropNames");
            Method getCrop = pamRegistry.getMethod("getCrop", String.class);
            for (String name : (String[]) names.get(null)) {
                BlockCrops crop = (BlockCrops) getCrop.invoke(null, name);
                HarvestRegistry.registerStandardCrop(crop, crop.getMaxAge());
            }
        } catch (ClassNotFoundException e) {
            BMLog.DEFAULT.error("HarvestCraft integration cancelled; unable to find registry class");
        } catch (NoSuchMethodException | NoSuchFieldException e) {
            BMLog.DEFAULT.error("HarvestCraft integration cancelled; unable to find crop name mapper");
        } catch (IllegalAccessException | InvocationTargetException e) {
            BMLog.DEFAULT.error("HarvestCraft integration cancelled; crop name lookup broke");
        }
    }







    private static void addMysCrops() {
    	addThirdPartyCrop("mysticalagriculture", "abyssalnite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "adamantine_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "aluminum_brass_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "aluminum_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "alumite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "amber_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "apatite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "aquamarine_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "aquarium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "ardite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "basalt_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "basalz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "black_quartz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "blaze_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "blitz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "blizz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "blue_topaz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "boron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "brass_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "bronze_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "certus_quartz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "chicken_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "chimerite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "chrome_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "coal_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "cobalt_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "cold_iron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "compressed_iron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "conductive_iron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "constantan_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "copper_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "coralium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "cow_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "creeper_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "dark_gem_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "dark_steel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "dawnstone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "desh_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "diamond_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "dilithium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "dirt_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "draconium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "dreadium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "dye_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "electrical_steel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "electrotine_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "electrum_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "elementium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "emerald_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "enderium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "enderman_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "ender_amethyst_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "ender_biotite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "end_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "end_steel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "energetic_alloy_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "experience_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "fiery_ingot_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "fire_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "fluix_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "fluxed_electrum_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "ghast_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "glowstone_ingot_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "glowstone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "gold_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "grains_of_infinity_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "graphite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "guardian_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "hop_graphite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "ice_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "invar_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "iridium_ore_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "iridium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "ironwood_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "iron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "jade_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "knightmetal_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "knightslime_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "lapis_lazuli_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "lead_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "limestone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "lithium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "lumium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "magnesium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "malachite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "manasteel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "manyullyn_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "marble_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "menril_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "meteoric_iron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "mithril_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "moonstone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "mystical_flower_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "nature_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "nether_quartz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "nether_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "nickel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "obsidian_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "octine_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "osmium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "peridot_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "pig_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "platinum_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "pulsating_iron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "quartz_enriched_iron_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "quicksilver_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "rabbit_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "redstone_alloy_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "redstone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "refined_obsidian_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "rock_crystal_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "rubber_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "ruby_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "saltpeter_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "sapphire_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "seed_reprocessor", 7);
		addThirdPartyCrop("mysticalagriculture", "sheep_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "signalum_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "silicon_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "silver_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "skeleton_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "sky_stone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "slate_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "slime_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "slimy_bone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "soularium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "spider_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "starmetal_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "star_steel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "steeleaf_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "steel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "stone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "sulfur_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "sunstone_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "syrmorite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tanzanite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "terrasteel_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "thaumium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "thorium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tier1_inferium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tier2_inferium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tier3_inferium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tier4_inferium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tier5_inferium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tin_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "titanium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "topaz_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tritanium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "tungsten_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "uranium_238_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "uranium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "valonite_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "vibrant_alloy_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "vinteum_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "void_metal_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "water_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "wither_skeleton_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "wood_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "yellorium_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "zinc_crop", 7);
		addThirdPartyCrop("mysticalagriculture", "zombie_crop", 7);

		addThirdPartyCrop("mysticalagradditions", "awakened_draconium_crop", 7);
		addThirdPartyCrop("mysticalagradditions", "dragon_egg_crop", 7);
		addThirdPartyCrop("mysticalagradditions", "nether_star_crop", 7);
		addThirdPartyCrop("mysticalagradditions", "neutronium_crop", 7);
		addThirdPartyCrop("mysticalagradditions", "tier6_inferium_crop", 7);
    }
}
