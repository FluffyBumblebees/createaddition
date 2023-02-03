package com.mrh0.createaddition.util.liquid_burning;

import com.mrh0.createaddition.blocks.liquid_blaze_burner.LiquidBlazeBurnerTileEntity;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;

import static com.mrh0.createaddition.blocks.liquid_blaze_burner.LiquidBlazeBurnerTileEntity.FuelType.NORMAL;
import static com.mrh0.createaddition.blocks.liquid_blaze_burner.LiquidBlazeBurnerTileEntity.FuelType.SPECIAL;

/**
 * This class allows a tag name to be its own recipe builder, in this instance,
 * specifically regarding fluid fuels for the Liquid Blaze Burner. In order to
 * add your own recipe using this, just name your tag to this:
 * ["burnable_fuel_{YOUR_BURNING_TIME}"].
 * The semantics are open to change as support for "heated" and "superheated"
 * needs to added as well. So in the future, your tag names would look like this:
 * ["burnable_fuel_{HEAT_TYPE}_{YOUR_BURNING_TIME}"]. But the old semantic
 * will still be supported, and simply just default to "heated". So you
 * don't need to worry about breaking changes.
 */
public final class BurnableFuelTagResolver {

   public static boolean argsToTag(final Fluid fluid, final Args args) {
        for (final TagKey<Fluid> tagKey : fluid.defaultFluidState().getTags().toList()) {
            int i;
            boolean crashOnLow = true;
            try {
                i = Integer.parseInt(tagKey
                        .toString()
                        .replaceAll("[^0-9]+", "")
                        .trim()
                );
            } catch (NumberFormatException e) {
                i = 0;
                crashOnLow = false;
            }

            final LiquidBlazeBurnerTileEntity.FuelType type;
            final var processed_type = tagKey.location().getPath().replaceAll("burnable_fuel_", "")
                    .replaceAll("[0-9]+", "").replaceAll("_", "");

            if (tagKey.location().getNamespace().equals("createaddition") && tagKey.location().getPath()
                    .equals("burnable_fuel_" + processed_type + "_" + i)) {

                switch (processed_type) {
                    case ("heated") -> type = NORMAL;
                    case ("superheated") -> type = SPECIAL;
                    default -> type = null;
                }

                BurnableTagProperties tagProperties = new BurnableTagProperties(
                        tagKey,
                        type,
                        i,
                        crashOnLow
                );

                if (Boolean.TRUE.equals(args.args(tagProperties, tagKey))) {
                    return true;
                }
            }
        }
        return false;
    }


    public interface Args {
        @Nullable Boolean args(BurnableTagProperties tagProperties, TagKey<Fluid> tagKey);
    }
}
