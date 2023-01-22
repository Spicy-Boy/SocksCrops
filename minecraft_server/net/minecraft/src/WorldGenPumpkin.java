package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator
{
	private static ArrayList<BiomeGenBase> validBiomeList = new ArrayList();
	
	public static boolean isBiomeValid(BiomeGenBase biome) {
		return validBiomeList.contains(biome);
	}
	
	public static void addBiomeToGenerator(BiomeGenBase biome) {
		validBiomeList.add(biome);
	}
	
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
    	// FCMOD: Added
        boolean bIsValidBiome = isBiomeValid(par1World.getBiomeGenForCoords(par3, par5));
        int iPlacedPumpkinCount = 0;
       
        boolean bIsFresh = CheckIfFresh( par1World, par3, par5 );
        // END FCMOD

        for (int var6 = 0; var6 < 64; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(var7, var8, var9) && par1World.getBlockId(var7, var8 - 1, var9) == Block.grass.blockID && Block.pumpkin.canPlaceBlockAt(par1World, var7, var8, var9))
            {
            	// FCMOD: Changed
            	/*
                par1World.setBlock(var7, var8, var9, Block.pumpkin.blockID, par2Random.nextInt(4), 2);
                */
            	int iFacing = par2Random.nextInt( 4 ); // must be done regardless of whether the block is placed to avoid disrupting the random number generator
            	int r = par2Random.nextInt(5);
            	
            	if ( bIsValidBiome && iPlacedPumpkinCount < 3 )
            	{
            		//SCADDON: Changed
					if ( bIsFresh )
	        		{
	        			switch (r) {
	        			default:
						case 0:
							par1World.setBlock(var7, var8, var9, SCDefs.pumpkinOrange.blockID, iFacing + 12, 2);
							break;
						
						case 1:
							par1World.setBlock(var7, var8, var9, SCDefs.pumpkinGreen.blockID, iFacing + 12, 2);
							break;
						
						case 2:
							par1World.setBlock(var7, var8, var9, SCDefs.pumpkinYellow.blockID, iFacing + 12, 2);
							break;
						
						case 3:
							par1World.setBlock(var7, var8, var9, SCDefs.pumpkinWhite.blockID, iFacing + 12, 2);
							break;
						}
	        		}
            		else
            		{
            			par1World.setBlock(var7, var8, var9, SCDefs.pumpkinCarved.blockID, iFacing + (r * 4), 2);
            		}

                    iPlacedPumpkinCount++;
            	}
            	// END FCMOD            		
            }
        }

        return true;
    }
    
	// FCMOD: Added
    private final static double m_dDistForFreshPumpkins = 2500D;
    private final static double m_dDistSquaredForFreshPumpkins = ( m_dDistForFreshPumpkins * m_dDistForFreshPumpkins );
    
    public boolean CheckIfFresh( World world, int i, int k )
    {
    	int iSpawnX = world.getWorldInfo().getSpawnX();
    	int iSpawnZ = world.getWorldInfo().getSpawnZ();
    	
    	double dDeltaX = (double)( iSpawnX - i );
    	double dDeltaZ = (double)( iSpawnZ - k );
    	
    	double dDistSqFromSpawn = dDeltaX * dDeltaX + dDeltaZ * dDeltaZ;
    	
    	return dDistSqFromSpawn > m_dDistSquaredForFreshPumpkins;
}
    // END FCMOD	
}
