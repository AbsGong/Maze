import java.util.ArrayList;


public class Cell implements Comparable{
	
	private int indexX;
	private int indexY;
	/*g() value for actual cost*/
	private int gvalue = Integer.MAX_VALUE;
	/*h() value for estimate cost*/
	private int hvalue = Integer.MAX_VALUE;
	private Cell parent = null;
	private Cell goal = null;
	private double evaluateValue = Integer.MAX_VALUE;
	
	public Cell(int indexX, int indexY){
		this.indexX = indexX;
		this.indexY = indexY;
		this.hvalue = 0;
	}
	public Cell(int indexX, int indexY, Cell goal){
		this.indexX = indexX;
		this.indexY = indexY;
		this.gvalue = 0;
		this.goal = goal;
		this.hvalue = calculatehvalue();
		this.evaluateValue = calEvaluate();
	}
	
	public Cell(int indexX, int indexY, Cell goal, Cell parent){
		this.indexX = indexX;
		this.indexY = indexY;
		this.parent = parent;
		this.goal = goal;
		setGvalue();
		this.hvalue = calculatehvalue();
		this.evaluateValue = calEvaluate();
	}
	
	public int getIndexX(){
		return indexX;
	}
	
	public int getIndexY(){
		return indexY;
	}
	
	public int getGvalue(){
		return gvalue;
	}
	/* set the g() value, g() value actually is a count of how many
	 * steps has gone*/
	public void setGvalue(){
		gvalue = parent.getGvalue() + 1;
	}
	/*return h() value hvalue*/
	public int getHvalue(){
		return hvalue;
	}
	
	/* calculate the h() value from the current cell to the goal cell
	 * calculate the MD of current cell to goal cell 
	 * return the MD distance*/
	public int calculatehvalue(){
		int goalIndexX = goal.getIndexX();
		int goalIndexY = goal.getIndexY();
		int MD = -1;
		MD = Math.abs(this.indexX - goalIndexX) + Math.abs(this.indexY - goalIndexY);
		return MD;
	}
	
	public double calEvaluate(){
		return (this.gvalue + this.hvalue);
	}
	/*return the child nodes for a cell and create the link of parent
	 * Check the child is acceptable by the parameter board
	 * board is a two d array is like {1,2,0,0,0,0},{2,1,0,0,3,3},{1,2,3,4,5,6}*/
	public ArrayList<Cell> getChildren(){
		ArrayList<Cell> children = new ArrayList<Cell>();
		if (Maze.isSpace(indexX - 1, indexY)){
			
			Cell child = new Cell(indexX - 1, indexY, this.goal, this);	
			
			children.add(child);
		}
		if (Maze.isSpace(indexX, indexY - 1)){
			Cell child = new Cell(indexX, indexY - 1, this.goal, this);	
			children.add(child);
		}
		if (Maze.isSpace(indexX + 1, indexY)){
			Cell child = new Cell(indexX + 1, indexY, this.goal, this);	
			children.add(child);
		}
		if (Maze.isSpace(indexX, indexY + 1)){
			Cell child = new Cell(indexX, indexY + 1, this.goal, this);	
			children.add(child);
		}
		return children;
	}
	
	public String test(){
		String result = "";
		result += "position:(" + indexX + "," + indexY + "); ";
		result += "g():" + gvalue + "; ";
		result += "h():" + hvalue + "; ";
		result += "f():" + evaluateValue + "; ";
		result += "goal:(" + goal.getIndexX() + "," + goal.getIndexY() + "); ";
		if (parent != null){
			result += "parent:(" + parent.getIndexX() + "," + parent.getIndexY() + ")\n";
		}
		else {
			result += "parent: null" ;
		}
		return result;
	}

	

	/**
	public static void main(String[] args){
		Cell goal = new Cell(0,5);
		/*
		Cell level1 = new Cell(0,0,goal);
		Cell level2 = new Cell(1,0,goal,level1);
		Cell level3 = new Cell(2,0,goal,level2);
		Cell level31 = new Cell(1,1,goal,level2);
		
		Cell cell11 = new Cell(1,1,goal);
		
		//System.out.println(level1.test());
		//System.out.println(level2.test());
		ArrayList<Cell> children = cell11.getChildren();
		
		for(int i = 0; i < children.get(0).getChildren().size(); i++){
			System.out.println(children.get(0).getChildren().get(i).test());	
		}
		
		
		for(int i = 0; i < children.size(); i++){
			System.out.println(children.get(i).test());
		}
		
		//System.out.println(Maze.isSpace(1, 1));
		
		System.out.println("level1 g():"+level1.getGvalue()+" h()"+level1.getHvalue());
		System.out.println("level2 g():"+level2.getGvalue()+" h()"+level2.getHvalue());
		System.out.println("level3 g():"+level3.getGvalue()+" h()"+level3.getHvalue());
		System.out.println("level31 g():"+level31.getGvalue()+" h()"+level31.getHvalue());
		PriorityQueue<Cell> openQueue = new PriorityQueue<Cell>();
		openQueue.add(cell11);
		for(int i = 0; i < children.size(); i++){
			openQueue.add(children.get(i));
		}
		for(int i = 0; i <= openQueue.size(); i++)
			System.out.println(openQueue.poll().test());
		
	}
	**/
	
	public double getEvaluateValue(){
		return evaluateValue;
	}
	public void setVisited(){
		Maze.visited[indexX][indexY] = true;
	}
	public boolean isGoal(){
		if (indexX == goal.indexX && indexY == goal.indexY)
			return true;
		else 
			return false;
	}
	public Cell getParent(){
		return parent;
	}
	@Override
	public int compareTo(Object o1) {
		Cell cell = (Cell)o1;
		if(cell.getEvaluateValue() < this.evaluateValue){
			return 1;
		}
		else
			return -1;
	}
	
	
}
