//COPY OF DecoBlockSapling
package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class SCBlockSapling extends BlockFlower
{
    private static final double width = 0.8D;
    public String[] baseTextureNames;

    protected SCBlockSapling(int id) {
        super(id);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.SetFurnaceBurnTime(FCEnumFurnaceBurnTime.KINDLING);
        this.SetFilterableProperties(0);
        this.InitBlockBounds(0.1D, 0.0D, 0.1D, width + 0.1D, width, width + 0.1D);
        this.SetBuoyant();
        this.setStepSound(Block.soundGrassFootstep);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        this.checkFlowerChange(world, x, y, z);

        if (!CanGrowOnBlock(world, x, y - 1, z)) return;
        
        if (world.provider.dimensionId != 1 && world.getBlockId(x, y, z) == this.blockID && world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(16) == 0) {
            int meta = world.getBlockMetadata(x, y, z);
            int growthStage = (meta & -4) >> 2;
            
            //System.out.println(meta + growthStage);

            if (growthStage < 3) {
                growthStage++;
                meta = meta & 3 | growthStage << 2;
                world.setBlockMetadataWithNotify(x, y, z, meta);
            }
            else {
                this.attemptToGrow(world, x, y, z, rand);
            }
        }
    }

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
    
    @Override
    public boolean OnBlockSawed(World world, int x, int y, int z) {
        return false;
    }

    @Override
    protected boolean CanGrowOnBlock(World world, int x, int y, int z) {
        Block var5 = Block.blocksList[world.getBlockId(x, y, z)];
        return var5 != null && var5.CanSaplingsGrowOnBlock(world, x, y, z);
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        int var5 = world.getBlockId(i, j, k);
        return var5 == 0 || blocksList[var5].blockMaterial.isReplaceable();
    }
    
    @Override
    public boolean canBlockStay(World world, int i, int j, int k) {

    	return FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, i, j-1, k, 1);
    	
    	// return super.canBlockStay(world, i, j, k);
    }
    
    //------ Class Specific Methods ------//
    
    public int getSaplingGrowthStage(int meta) {
        int growthStage = (meta & -4) >> 2;
        return growthStage;
    }
    
    public int getSaplingGrowthStage(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        int growthStage = (meta & -4) >> 2;
        return growthStage;
    }
    
    public int getSaplingGrowthStage(IBlockAccess blockAccess, int x, int y, int z) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        int growthStage = (meta & -4) >> 2;
        return growthStage;
    }

	public void attemptToGrow(World world, int x, int y, int z, Random rand) {
		int growthChance = 32;

		int blockIDBelow = world.getBlockId(x, y - 1, z);
		Block blockBelow = Block.blocksList[blockIDBelow];

		if (blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
			growthChance /= blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 1, z, this);
		}

		int metadata = world.getBlockMetadata(x, y, z);
		int growthStage = (metadata & (~3)) >> 2;

		if (growthStage == 3) {
			growthChance /= 2;
		}

		if (rand.nextInt(growthChance) == 0) {
			if (growthStage < 3) {
				growthStage++;
				metadata = (metadata & 3) | (growthStage << 2);

				world.setBlockMetadataWithNotify(x, y, z, metadata);

				if (growthStage == 3) {
					blockBelow.NotifyOfFullStagePlantGrowthOn(world, x, y - 1, z, this);
				}
			}
			else {
				if (!(blockBelow instanceof FCBlockPlanterBase) || blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
					growTree(world, x, y, z, rand);
				}
			}
		}
	}
	
    public abstract void growTree(World world, int x, int y, int z, Random rand);

    /**
     * Determines if the same sapling is present at the given location.
     */
    public boolean isSameSapling(World world, int x, int y, int z, int meta) {
        return world.getBlockId(x, y, z) == this.blockID && (world.getBlockMetadata(x, y, z) & 3) == meta;
    }
    
    //------Client Only Methods ------//
    
    private Icon[][] iconArray = new Icon[4][4];

}