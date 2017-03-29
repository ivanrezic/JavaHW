package hr.fer.zemris.java.hw04.db;

/**
 * The Class FieldValueGetters offers static methods used for
 * <code>StudentRecord</code> fetching.
 * 
 * @author Ivan
 */
public class FieldValueGetters {

	/** Getter method used for retrieving <code>StudentRecond</code> name. */
	public static final IFieldValueGetter FIRST_NAME;

	/** Getter method used for retrieving <code>StudentRecond</code> last name. */
	public static final IFieldValueGetter LAST_NAME;

	/** Getter method used for retrieving <code>StudentRecond</code> jmbag.*/
	public static final IFieldValueGetter JMBAG;

	static {
		FIRST_NAME = record -> record.getFristName();
		LAST_NAME = record -> record.getLastName();
		JMBAG = record -> record.getJmbag();
	}
}
