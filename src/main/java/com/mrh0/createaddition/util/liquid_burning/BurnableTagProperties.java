package com.mrh0.createaddition.util.liquid_burning;

import com.mrh0.createaddition.CreateAddition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import static com.mrh0.createaddition.blocks.liquid_blaze_burner.LiquidBlazeBurnerTileEntity.FuelType;


public final class BurnableTagProperties {
    private final int time;
    private final ResourceLocation tagResource;
    private final FuelType fuelType;

    public BurnableTagProperties(final TagKey<Fluid> tagKey, final FuelType fuelType, final int timePerBucket,
                                 final boolean crashOnLow) {
        this.fuelType = fuelType;

        if (fuelType == null) {
            throw new RuntimeException(tagKey + ". does not include a heat type. Please resolve this " +
                    "with the format of 'burnable_fuel_${FUEL_TYPE}_${FUEL_TIME}'."
            );
        }

        final String fuelTagType;
        switch (fuelType) {
            case NORMAL -> fuelTagType = "heated";
            case SPECIAL -> fuelTagType = "superheated";
            default -> throw new RuntimeException("FuelType doesn't have a usable value! Tag = " + tagKey);
        }

        this.tagResource = new ResourceLocation(CreateAddition.MODID ,
                "burnable_fuel_" + fuelTagType + "_" + timePerBucket
        );

        if (timePerBucket < 200 && crashOnLow) {
            throw new RuntimeException("Burning Time cannot be less then 200! Change this to stop this crash " +
                    "\n[Conflicting Tag ---> " + tagResource + "]\n");
        }

        final int i = timePerBucket / 100;

        if (i * 100 != timePerBucket ) {
            throw new RuntimeException("Burning Time is indivisible by 100! Change this to stop this crash " +
                    "\n[Conflicting Tag ---> " + tagResource + "]\n");
        }

        this.time = timePerBucket;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    public int getTime() {
        return this.time;
    }

    public int getDropletAmount() {
        return 81000;
    }

    public ResourceLocation asResource() {
        return this.tagResource;
    }
}
