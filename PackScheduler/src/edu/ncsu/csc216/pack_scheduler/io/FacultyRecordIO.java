/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Deals with reading and writing of files with information on Faculty users.
 * Methods deal with loading files with valid Faculty input, or saving files
 * with valid Faculty input. This class is used in other places for this
 * functionality.
 * 
 * @author Benson Liu
 *
 */
public class FacultyRecordIO {
	/**
	 * Returns a LinkedList of Faculty objects from the location specified in the
	 * fileName String. An IllegalArgumentException is thrown if the file cannot be
	 * found.
	 * 
	 * @param fileName Name of file being read
	 * @return LinkedList of Faculty from file
	 * @throws FileNotFoundException If file cannot be found an
	 *                               IllegalArgumentException is thrown.
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = null;
		try {
			fileReader = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(fileName + " (The system cannot find the file specified)");
		}

		LinkedList<Faculty> students = new LinkedList<Faculty>();
		while (fileReader.hasNextLine()) {
			try {
				Faculty faculty = processFaculty(fileReader.nextLine());
				students.add(faculty);
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return students;
	}

	/**
	 * Tries to create a new Faculty object based on a line that is brought in from
	 * a file. An IllegalArgumentException is thrown if there is error processing
	 * the line.
	 *
	 * @param line the current line of the file
	 * @return the Faculty object if one was created
	 */
	private static Faculty processFaculty(String line) {
		Faculty f = null;
		Scanner sc = new Scanner(line);
		sc.useDelimiter(",");
		String[] elements = new String[6];
		int i = 0;
		try {
			while (sc.hasNext()) {
				elements[i] = sc.next();
				i++;
			}
			f = new Faculty(elements[0], elements[1], elements[2], elements[3], elements[4],
					Integer.parseInt(elements[5]));
		} catch (NullPointerException e) {
			sc.close();
			throw new IllegalArgumentException();
		}
		sc.close();
		return f;
	}

	/**
	 * Writes a LinkedList of Faculty type onto a file. Throws an IOException if
	 * there is an issue writing the file.
	 * 
	 * @param fileName         Name of file being written
	 * @param facultyDirectory LinkedList of Faculty being written
	 * @throws IOException Thrown if there is an error writing the file.
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = null;
		try {
			fileWriter = new PrintStream(new File(fileName));
		} catch (IOException e) {
			throw new IOException(fileName + " (Permission denied)");
		}

		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}

		fileWriter.close();
	}
}
