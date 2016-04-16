package optimizer;

import java.io.FileNotFoundException;
import java.io.IOException;

import heap.FieldNumberOutOfBoundException;
import heap.InvalidTupleSizeException;
import heap.InvalidTypeException;
import heap.Tuple;

public abstract class ISelectivityEstimator {

	private String relation1, relation2;
	private String[][] conditions;

	/**
	 * @param relation1 file path of the first relation 
	 * @param relation2 file path of the second relation 
	 * @param conditions an array of conditions for IE Join [ [R,3,4,S,3], [R,4,3,S,4] ]
	 */
	public ISelectivityEstimator(String relation1, String relation2, String[][] conditions) {
		this.relation1 = relation1;
		this.relation2 = relation2;
		this.conditions = conditions;
	}

	public abstract int estimate(int r1SampleSize, int r2SampleSize) throws FileNotFoundException, IOException, NumberFormatException,
			InvalidTypeException, InvalidTupleSizeException, FieldNumberOutOfBoundException;

	/**
	 * Swap columns of the relation
	 * @param r1 relation of interest
	 * @param condition1_col one based index which will be swapped with column 2
	 * @param condition2_col one based index which will be swapped with column 3
	 */
	protected void swapColumnsToMatch(Tuple[] r1, int condition1_col, int condition2_col)
			throws IOException, FieldNumberOutOfBoundException {
		for (Tuple tuple : r1) {
			// Check if existing columns are already in the right spots
			if (condition1_col != 1) {
				// swap columns
				int current_val = tuple.getIntFld(2);
				tuple.setIntFld(2, tuple.getIntFld(condition1_col + 1));
				tuple.setIntFld(condition1_col + 1, current_val);
			}
			if (condition2_col != 2) {
				// swap columns
				int current_val = tuple.getIntFld(3);
				tuple.setIntFld(3, tuple.getIntFld(condition2_col + 1));
				tuple.setIntFld(condition2_col + 1, current_val);
			}
		}
	}

	protected String getRelation1() {
		return relation1;
	}

	protected String getRelation2() {
		return relation2;
	}

	protected String[][] getConditions() {
		return conditions;
	}
}
