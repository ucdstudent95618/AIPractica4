import aima.core.search.framework.problem.GoalTest;

public class ExamGoalTest implements GoalTest {

	public boolean isGoalState(Object state) {
		ExamBoard board = (ExamBoard) state;
		return board.getNumberOfProfessorsOnBoard() == ExamDemo.turnosNecesarios 
				&& board.getNumberOfViolatedRestrictions() == 0;
		
		
		
	}
}