package org.jglrxavpok.redstonepixels;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PixelBlock extends Block {

    public static final IntegerProperty POWER = IntegerProperty.create("power", 0, 15);

    public PixelBlock() {
        super(AbstractBlock.Properties.create(Material.REDSTONE_LIGHT)
                .hardnessAndResistance(0.3F)
                .sound(SoundType.GLASS)
                .allowsSpawning((_0,_1,_2,_3) -> true)
        );

        setRegistryName(RedstonePixels.MODID, "redstone_pixel");
        setDefaultState(getStateContainer().getBaseState().with(POWER, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos positionInWorld, Block p_220069_4_, BlockPos p_220069_5_, boolean moving) {
        int powerLevel = world.getRedstonePowerFromNeighbors(positionInWorld);
        int currentPowerLevel = state.get(POWER);
        if(powerLevel != currentPowerLevel) {
            world.setBlockState(positionInWorld, state.with(POWER, powerLevel));
        }
    }
}
