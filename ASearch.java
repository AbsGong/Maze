import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

public class ASearch {
	private static PriorityQueue<Cell> openQueue = new PriorityQueue<Cell>();
	private static Cell search(){
		while(!openQueue.isEmpty()){
			
			Cell currentCell = openQueue.poll();
			//System.out.println(currentCell.test());
			if(currentCell.isGoal()){
				return currentCell;
			}
			currentCell.setVisited();
			ArrayList<Cell> children = currentCell.getChildren();
			for (Cell cell: children){
				openQueue.add(cell);
			}
		}
		return null;
	}
	public static ArrayList<Cell> startSearch(Cell start, Cell goal){
		
		openQueue.add(start);
		Cell solutionCell = search();
		//System.out.println(search().test());
		ArrayList<Cell> solution = new ArrayList<Cell>();
		while ( solutionCell.getParent()!=null){
			//System.out.println(solutionCell.getParent().test());
			solution.add(solutionCell.getParent());
			solutionCell = solutionCell.getParent();
		}
		for(Cell cell:solution){
			System.out.println(cell.test());
		}
		
		return solution;
	}
}
