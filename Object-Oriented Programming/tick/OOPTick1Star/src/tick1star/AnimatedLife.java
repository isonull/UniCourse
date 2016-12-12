package tick1star;

public class AnimatedLife {

    private boolean[][] mWorld;
    private Pattern mPattern;
    
    public boolean[][] getWorld() {
    	return mWorld;
    }
    
    public AnimatedLife(String format) throws PatternFormatException {
        //TODO: Determine the dimensions of the game board
    	//format example: Glider:Richard Guy:20:20:1:1:010 001 111
    	mPattern = new Pattern(format);
        mWorld  = new boolean[mPattern.getHeight()][mPattern.getWidth()];
        
        mPattern.initialise(mWorld);
            //TODO: Using loops, update the appropriate cells of 'world'
            //      to 'true'
            // ...
    }

    // ...
    
    public boolean getCell(int col, int row) {
    	   if (row < 0 || row >= mPattern.getHeight()) return false;
    	   if (col < 0 || col >= mPattern.getWidth()) return false;

    	   return mWorld[row][col];
    	}

    public void setCell(boolean[][] world, int col, int row, boolean value){
        world[row][col] = value;
    }

    public void print() {
        for(int i = 0; i != mWorld.length; ++i){
        for(int j = 0; j != mWorld[i].length; ++j){
            System.out.print(getCell(j,i) ? "#" : "_");
        }
        System.out.println("");
        }
    }

    private int countNeighbours(int col, int row) {
        int n = 0;
        if(col >= 0 && col < mPattern.getWidth() && row >= 0 && row < mPattern.getHeight()){
            n = (col >= 1 && row >= 1              							   && getCell(col-1,row-1)) ? ++n : n ;
            n = (col >= 1 && row < mPattern.getHeight()-1       			   && getCell(col-1,row+1)) ? ++n : n ;  
            n = (col < mPattern.getWidth()-1 && row >= 1        			   && getCell(col+1,row-1)) ? ++n : n ; 
            n = (col < mPattern.getWidth()-1 && row < mPattern.getHeight()-1 && getCell(col+1,row+1)) ? ++n : n ; 
            n = (col >= 1                          							   && getCell(col-1,row)) ? ++n : n ; 
            n = (col < mPattern.getWidth()-1                    			   && getCell(col+1,row)) ? ++n : n ; 
            n = (row >= 1                         							   && getCell(col,row-1)) ? ++n : n ; 
            n = (row < mPattern.getHeight()-1                   			   && getCell(col,row+1)) ? ++n : n ;
        } else { return 0;}
        return n;

    }

    private boolean computeCell(int col, int row) {
        int i = countNeighbours(col,row);
        if(i < 2 || i > 3) return false;
        if(i == 3) return true;
        if(i == 2 && getCell(col,row)) return true;
        else return false;
    }

    public void nextGeneration() {
    	boolean[][] nextGeneration = new boolean[mWorld.length][];
        for (int y = 0; y < mWorld.length; ++y) {
            nextGeneration[y] = new boolean[mWorld[y].length];
            for (int x = 0; x < mWorld[y].length; ++x) {
                boolean nextCell = computeCell(x, y);
                nextGeneration[y][x]=nextCell;
            }
        }
        mWorld = nextGeneration;
    }
    
    public void play() throws Exception {
        int userResponse = 0;
        while (userResponse != 'q') {
            print();
            userResponse = System.in.read();
            nextGeneration();
        }
    }
    
    public static void main(String[] args) throws Exception {
    	
    	
    	
    	AnimatedLife al = new AnimatedLife(args[0]);    	
    	int numberOfImage = Integer.parseInt(args[1]);	
    	String fileName = args[2];
    	
    	System.out.println(fileName);
    	
    	OutputAnimatedGif image = new OutputAnimatedGif(fileName);
    	image.addFrame(al.getWorld());
    	
    	for(int i = 0; i < numberOfImage-1; ++i){
    		al.nextGeneration();
    		image.addFrame(al.getWorld());
    	}
    	
    }
}