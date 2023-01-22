package net.minecraft.src;

import java.util.Random;

public class SCBlockCompost extends FCBlockFalling {

	
	protected SCBlockCompost(int par1) {
        super(par1, Material.ground);
        
        this.setHardness( 0.5F );
        
        this.SetShovelsEffectiveOn();
        
        this.SetBuoyant();
        
        this.setStepSound( soundGravelFootstep );
        
        this.setUnlocalizedName("SCBlockCompost");
        
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

	
	
	
	public boolean GetCanBiomeGrassSpreadToBlock( World world, int i, int j, int k )
	{
        Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
        
        if ( (blockAbove == null || blockAbove.GetCanGrassGrowUnderBlock( world, i, j + 1, k, false )) ) 
        {            
        	return true;
        }
    	
    	return false;
	}
	
	
    public boolean SpreadBiomeGrassToBlock(World world, int x, int y, int z, int biomeType)
    {
        world.setBlockAndMetadataWithNotify(x, y, z, SCDefs.biomeGrassCompost.blockID, biomeType);
        
    	return true;
    }
	
	
	@Override
	public boolean CanWildVegetationGrowOnBlock(World world, int i, int j, int k)
	{
		return true;
	}
	
	@Override
	public boolean CanReedsGrowOnBlock(World world, int i, int j, int k)
	{	
		return true;
	}
	
	@Override
	public boolean CanSaplingsGrowOnBlock(World world, int i, int j, int k) {
		return true;
	}

	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock)
	{
		return 2F;
	}
	
	@Override
	public boolean GetIsFertilizedForPlantGrowth(World world, int i, int j, int k) {
		return true;
	}
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		// revert back to soil
		world.setBlockAndMetadataWithNotify( i, j, k, 
			FCBetterThanWolves.fcBlockDirtLoose.blockID, 0 );
	}
	
}