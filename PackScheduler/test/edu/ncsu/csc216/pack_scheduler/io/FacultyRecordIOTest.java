/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;


/**
 * Test for the FacultyRecordIO class
 * @author Benson Liu
 *
 */
public class FacultyRecordIOTest {

	/** Valid course records. */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid course records. */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty1 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty2 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty3 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty4 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty5 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty6 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty7 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	/** String containing the information of a valid student from validTestFile. */
	private String validFaculty8 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";
	/** Array of valid students from student_records.txt. */
	private String[] validFaculty = { validFaculty1, validFaculty2, validFaculty3, validFaculty4, validFaculty5,
			validFaculty6, validFaculty7, validFaculty8};
	/** String for hash password. */
	private String hashPW;
	/** String for storing Hash Algorithm. */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Check files.
	 *
	 * @param expFile the exp file
	 * @param actFile the act file
	 * @return true, if successful
	 */
	private boolean checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
			return true;
		} catch (IOException e) {
			fail("Error reading files.");
			return false;
		}
	}

	/**
	 * This method sets up files for testing before calling each method.
	 */
	@Before
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}
	
	/**
	 * Test hidden FacultyRecordIO constructor.
	 */
	@Test
	public void testConstructor() {
		FacultyRecordIO io = new FacultyRecordIO();
		assertNotNull(io);
		
	}

	/**
	 * Tests the readStudentRecords method which reads the data given in the file
	 * provided.
	 * 
	 * @throws FileNotFoundException if the file from which data has to be read is
	 *                               not found
	 */
	@Test
	public void testReadFacultyRecords() throws FileNotFoundException {
		try {
			LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, faculty.size());

			for (int i = 0; i < validFaculty.length; i++) {
				assertEquals(validFaculty[i], faculty.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
		try {
			LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			LinkedList<Faculty> newStudent = new LinkedList<Faculty>();
			assertEquals(faculty, newStudent);
		} catch (FileNotFoundException e) {
			assertEquals(e.getMessage(), invalidTestFile + " (The system cannot find the file specified)");
		}
		try {
			FacultyRecordIO.readFacultyRecords("notafile.txt");
			fail();
		} catch (FileNotFoundException e) {
			assertEquals("notafile.txt (The system cannot find the file specified)", e.getMessage());
		}
		
	}

	/**
	 * Tests the writesStudentRecords method which writes the students in the
	 * studentDirectory.
	 */
	@Test
	public void testWriteFacultyRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_record.txt", faculty);
		} catch (IOException e) {
			fail();
		}
		assertTrue(checkFiles("test-files/student_records.txt", "test-files/result_file.txt"));
	}

	/**
	 * This tests writing the student in a student directory that doesn't exist or
	 * for which it does't have permission to write.
	 */
	@Test
	public void testWriteFacultyRecordsNoPermissions() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 3));
		// Assumption that you are using a hash of "pw" stored in hashPW

		try {
			FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_faculty_records.txt", faculty);
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IOException e) {
			assertEquals("/home/sesmith5/actual_faculty_records.txt (Permission denied)", e.getMessage());
			// The actual error message on Jenkins!
		}

	}

}
