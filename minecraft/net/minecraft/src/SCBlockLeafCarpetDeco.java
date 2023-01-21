package net.minecraft.src;

public class SCBlockLeafCarpetDeco extends SCBlockLeafCarpet {
	
	public final static int ACACIA = 0;
	public final static int AUTUMN_RED = 1;
	public final static int AUTUMN_ORANGE = 2;
	public final static int AUTUMN_YELLOW = 3;
	
	public static String[] carpetTypes = {
    		"acaia",
    		"autumnRed", "autumnOrange", "autumnYellow"
    };
	
	protected SCBlockLeafCarpetDeco(int blockID) {
		super(blockID);
		setUnlocalizedName("SCBlockLeafCarpetDeco");
	}

    Icon[] leaves = new Icon[16];
    private String[] leavesTex = {
    		"decoBlockLeavesAcacia", "decoBlockLeavesAutumnRed", "decoBlockLeavesAutumnOrange", "decoBlockLeavesAutumnYellow",
    };
	
    Icon[] opaqueLeaves = new Icon[16];
    private String[] opaqueLeavesTex = {
    		"decoBlockLeavesAcacia", "decoBlockLeavesAutumnRed", "decoBlockLeavesAutumnOrange", "decoBlockLeavesAutumnYellow",
    };
    
	@Override
	public void registerIcons(IconRegister register)
	{
    	for (int i = 0; i < carpetTypes.length; i++)
    	{
    		leaves[i] = register.registerIcon(leavesTex[i]);
    		opaqueLeaves[i] = register.registerIcon(opaqueLeavesTex[i]);
    	}
	}
	
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	//NO flower pls
    }
}