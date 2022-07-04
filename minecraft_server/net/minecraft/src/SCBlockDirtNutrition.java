package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockDirtNutrition extends FCBlockDirt {

	public static String[] nutritionLevelNames = new String[] {"dirt0", "dirt1", "dirt2", "dirt3"};
	
	protected SCBlockDirtNutrition( int blockID )
    {
        super( blockID);
        
        setHardness( 0.5F );
        SetShovelsEffectiveOn();
    	SetHoesEffectiveOn();
    	
    	setStepSound( soundGravelFootstep );
    	
    	setUnlocalizedName( "SCBlockDirtNutrition" );
        
        setCreativeTab( CreativeTabs.tabBlock );
    }
	
	//ADDON    
    private int getNutritionLevel( World world, int i, int j, int k) {
    	int meta = world.getBlockMetadata(i, j, k);
    	
    	if (meta == 0)
    	{
    		return 3;
    	}
    	else if (meta == 1) {
    		return 2;
    	}
    	else if (meta == 2 ) {
    		return 1;
    	}
    	else return 0;

	}
    
    @Override
	public int idDropped( int metadata, Random rand, int fortuneModifier )
	{
		return FCBetterThanWolves.fcBlockDirtLoose.blockID;
	}
	
	/**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta & 3;
    }
    
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int meta, float chanceOfDrop )
	{
		if (meta != 0) //nutri 3
		{
			DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileGravel.itemID, 3, 0, chanceOfDrop );
			DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileSand.itemID, 3, 0, chanceOfDrop );
		}
		else DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileDirt.itemID, 6, 0, chanceOfDrop );
		
		return true;
	}
	
    @Override
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	super.OnBlockDestroyedWithImproperTool( world, player, i, j, k, iMetadata );
    	
    	OnDirtDugWithImproperTool( world, i, j, k );    	
    }
    
	@Override
    public void onBlockDestroyedByExplosion( World world, int i, int j, int k, Explosion explosion )
    {
		super.onBlockDestroyedByExplosion( world, i, j, k, explosion );
		
    	OnDirtDugWithImproperTool( world, i, j, k );    	
    }
	
	@Override
    protected void OnNeighborDirtDugWithImproperTool( World world, int i, int j, int k, 
    	int iToFacing )
    {
		int meta = world.getBlockMetadata(i, j, k);
		
		if (meta == 0)
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0 );
		}
		else if (meta == 1 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1 );
		}
		else if (meta == 2 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2 );
		}
		else if (meta == 3 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3 );
		}
    }
    
	@Override
    public boolean GetCanGrassSpreadToBlock( World world, int i, int j, int k )
    {
        Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
        
        if ( blockAbove == null || blockAbove.GetCanGrassGrowUnderBlock( world, i, j + 1, k, false ) ) 
        {            
        	return true;
        }
    	
    	return false;
    }
    
	@Override
    public boolean SpreadGrassToBlock( World world, int i, int j, int k )
    {
		int meta = world.getBlockMetadata(i, j, k);
		
		if (meta == 0)
		{
			world.setBlockAndMetadataWithNotify( i, j, k, Block.grass.blockID, 0 );
		}
		else if (meta == 1 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, Block.grass.blockID, 1 );
		}
		else if (meta == 2 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, Block.grass.blockID, 2 );
		}
		else if (meta == 3 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, Block.grass.blockID, 3 );
		}
        
    	return true;
    }

	@Override
    public boolean GetCanMyceliumSpreadToBlock( World world, int i, int j, int k )
    {
		return !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j + 1, k, 0 );
    }
    
	@Override
    public boolean SpreadMyceliumToBlock( World world, int i, int j, int k )
    {
        world.setBlockWithNotify( i, j, k, Block.mycelium.blockID );
        
    	return true;
    }
    
    @Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
	public void OnVegetationAboveGrazed( World world, int i, int j, int k, EntityAnimal animal )
	{
        if ( animal.GetDisruptsEarthOnGraze() )
        {
        	int meta = world.getBlockMetadata(i, j, k);
    		
    		if (meta == 0)
    		{
    			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0 );
    		}
    		else if (meta == 1 )
    		{
    			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1 );
    		}
    		else if (meta == 2 )
    		{
    			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2 );
    		}
    		else if (meta == 3 )
    		{
    			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3 );
    		}
        	
        	NotifyNeighborsBlockDisrupted( world, i, j, k );
        }
	}
    
	@Override
    public boolean CanReedsGrowOnBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
	@Override
    public boolean CanSaplingsGrowOnBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
	@Override
    public boolean CanWildVegetationGrowOnBlock( World world, int i, int j, int k )
    {
    	return true;
    }
    
	@Override
    public boolean GetCanBlightSpreadToBlock( World world, int i, int j, int k, int iBlightLevel )
    {
		return true;
    }
	
	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return stack != null && stack.getItem() instanceof FCItemHoe;
    }
	
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	int meta = world.getBlockMetadata(i, j, k);
		
		if (meta == 0)
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0 );
		}
		else if (meta == 1 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1 );
		}
		else if (meta == 2 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2 );
		}
		else if (meta == 3 )
		{
			world.setBlockAndMetadataWithNotify( i, j, k, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3 );
		}

    	if ( !world.isRemote )
		{
            world.playAuxSFX( 2001, i, j, k, blockID ); // block break FX
		}
    	
    	return true;
    }
    
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
}
