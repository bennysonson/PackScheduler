package edu.ncsu.csc216.pack_scheduler.io;
import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * StudentRecordIO tests static methods provided in StudentRecordIO that support reading in student records from a file and writing student records to a file. 
 * @author Tanmay Jain
 * @author Boxiang Ma
 *
 */
public class StudentRecordIOTest {

	/**  Valid course records. */
	private final String validTestFile = "test-files/student_records.txt";
	/**  Invalid course records. */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
    /**String containing the information of a valid student from validTestFile.*/
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/**String containing the information of a valid student from validTestFile.*/
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	/**Array of valid students from student_records.txt.*/
	private String [] validStudents = {validStudent0, validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
			validStudent6, validStudent7, validStudent8, validStudent9};
	/**String for hash password.*/
	private String hashPW;
	/**String for storing Hash Algorithm.*/
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

			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
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

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}
	
	/**
	 * Test hidden CourseRecordIO constructor
	 */
	@Test
	public void testConstructor() {
		StudentRecordIO io = new StudentRecordIO();
		assertNotNull(io);
		
	}	
	
	/**
	 * Tests the readStudentRecords method which reads the data given in the file provided.
	 * @throws FileNotFoundException if the file from which data has to be read is not found
	 */
	@Test
	public void testReadStudentRecords() throws FileNotFoundException {
		try {
			SortedList<Student> student = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, student.size());

			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], student.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
		try {
			SortedList<Student> student = StudentRecordIO.readStudentRecords(invalidTestFile);			
			SortedList<Student> newStudent = new SortedList<Student>();
			assertEquals(student, newStudent);
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}
		try {
			StudentRecordIO.readStudentRecords("notafile.txt");
			fail();
		} catch (FileNotFoundException e) {
			assertEquals("notafile.txt (The system cannot find the file specified)", e.getMessage());
		}
	}
	/**
	 * Tests the writesStudentRecords method which writes the students in the studentDirectory.
	 */
	@Test
	public void testWriteStudentRecords() {
        SortedList<Student> students = new SortedList<Student>();
        try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_record.txt", students);
		} catch (IOException e) {
			fail();
		}
        assertTrue(checkFiles("test-files/student_records.txt", "test-files/result_file.txt"));
	}

	/**
	 * This tests writing the student in a student directory that doesn't exist or for which it does't have permission to write.
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		//Assumption that you are using a hash of "pw" stored in hashPW

		try {
			StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IOException e) {
			assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
			//The actual error message on Jenkins!
		}

	}

}
