



import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Does cool stuff
 * 
 * @author  Miguel Salazar
 * @author Andrea Markus Gentile
 */
public class ExamBoard {
	 
	int[] squares;
	
	
	final int size;

	/**
	 * Creates a board with <code>size</code> rows and size columns. Column and
	 * row indices start with 0.
	 */
	public ExamBoard(int size) { // should always be 16
		this.size = size;
		squares = new int[size];
		for (int i = 0; i < size; i++) {
				squares[i] = 0;
		}
	}

	/**
	 * Creates a board with <code>size</code> rows and size columns. Column and
	 * row indices start with 0.
	 * 
	 * @param config
	 *            Controls whether the board is initially empty or contains some
	 *            queens.
	 */
	
	/** Column and row indices start with 0! */
	public void addProfessorAt(Integer prof, Integer l) {
		if (!(professorExistsAt(l)))
			squares[l.intValue()] = prof.intValue();
	}

	public void removeProfessorFrom(Integer l) {
		if (professorExistsAt(l)) {
			squares[l.intValue()] = 0;
		}
	}

	/**
	 * Moves the queen in the specified column (x-value of <code>l</code>) to
	 * the specified row (y-value of <code>l</code>). The action assumes a
	 * complete-state formulation of the n-queens problem.
	 * 
	 * @param l
	 */

	public boolean professorExistsAt(Integer l) {
		return (professorExistsAt(l.intValue()));
	}

	private boolean professorExistsAt(int x) {
		return (squares[x] > 0);
	}

	public int getNumberOfProfessorsOnBoard() {
		int count = 0;
		for (int i = 0; i < size; i++) {
				if (squares[i] > 0)
					count++;
		}
		return count;
	}

	public List<Integer> getProfessorPositions() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		
		for (int j = 0; j<size; j++) {
			result.add(squares[j]);
		}
			
		return result;

	}
@Override
	public int hashCode() {
		List<Integer> locs = getProfessorPositions();
		int result = 17;
		for (Integer loc : locs) {
			result = 37 * loc.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (this.getClass() != o.getClass()))
			return false;
		ExamBoard aBoard = (ExamBoard) o;
		if (this.getNumberOfProfessorsOnBoard() != aBoard.getNumberOfProfessorsOnBoard())
			return false;
		boolean retVal = true;
		

		for (int i = 0; i<size; i++) {
			int pA = squares[i];
			int pB = aBoard.squares[i];
			if (pA != pB)
				retVal = false;
		}
		return retVal;
	}

	public void print() {
		System.out.println(getBoardPic());
	}

	public String getBoardPic() {
		StringBuffer buffer = new StringBuffer();
		for (int x = 0; (x < size); x++) {
			buffer.append(x+1 + ": ");
			if (professorExistsAt(x)) {
				//System.out.println(ExamDemo.profesores.get(squares[x]-1));
				buffer.append(ExamDemo.profesores.get(squares[x]-1).nombre);
			}
			else {
				buffer.append(" - ");
			}
			buffer.append("\n");
		}
		
		return buffer.toString();
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int x = 0; x <size; x++) {
			buf.append(x+1 + ": ");
			if (professorExistsAt(x)) {
				//System.out.println(ExamDemo.profesores.get(squares[x]-1));
				buf.append(ExamDemo.profesores.get(squares[x]-1).nombre);
			}
			else
				buf.append('-');
			
			buf.append("\n");
		}
		

		return buf.toString();
	}

	public int getNumberOfPreferencesApplied() {
		
	int preferences = 0;
		
		for(int i = 0; i<size; i++) {
			if(professorExistsAt(i)) {
				int profesorInPosition = squares[i]-1; // -1 because profesores go from 1 to x and array from 0 to x-1
				Profesor profesor = ExamDemo.profesores.get(profesorInPosition);
				
				for(Integer preferencia : profesor.preferencias) {
					if (preferencia == (i+1))
						preferences +=1;	
				}
			}
		}
		
		return preferences;
	}
	
	public int getNumberOfViolatedRestrictions() {
		int violations = 0;
		
		for(int i = 0; i<size; i++) {
			if(professorExistsAt(i)) {
				int profesorInPosition = squares[i]-1; // -1 because profesors go from 1 to x and array from 0 to x-1
				Profesor profesor = ExamDemo.profesores.get(profesorInPosition);
				//System.out.println("Profesor: "+ profesor.nombre);
				for(Integer restriccion : profesor.restricciones) {
				//	System.out.println(" "+restriccion);
					if (restriccion == (i+1))
						violations +=1;	
				}
			}
		}
		
		return violations;
	}

	public int workDays(int profesor) {
		int sum = 0;
		
		for(int i = 0; i<size; i++) 
			if(squares[i] == profesor) 
				sum +=1;
		
		
		return sum;
	}

	
}