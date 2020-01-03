package edu.ncsu.csc216.pack_scheduler.io;
import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


/**
 * The purpose of the StudentRecordIO class is to have specific functionality in order to read
 * and write information from files. The methods that are implemented within the object include
 * readStudentRecords which will read in input from a file, writeStudentRecords which exports the
 * current student records, and processStudent which will specifically allocate the information
 * for each student and move it where it needs to go. The StudentRecordIO object is used in multiple
 * other locations of the program to read and write to the file where students are held.  
 *
 * @author Cameron Dutra
 * @author Tanmay Jain
 * @author Boxiang Ma
 */
public class StudentRecordIO {

	/**
	 * Reads student records from a file.If the file to read cannot be found or the permissions are incorrect, a File NotFoundException is thrown.
	 * @param fileName name of the file to read information from
	 * @return students an ArrayList of students
	 * @throws FileNotFoundException if file is not found
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
        Scanner fileReader = null;
		try {
			fileReader = new Scanner(new FileInputStream(fileName));	
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(fileName + " (The system cannot find the file specified)");
		}

		SortedList<Student> students = new SortedList<Student>();
	    while (fileReader.hasNextLine()) {
	        try {
	            Student student = processStudent(fileReader.nextLine());
	            students.add(student);
	        } catch (IllegalArgumentException e) {
	            //skip the line
	        }
	    }
	    fileReader.close();
	    return students;
	}
	
	/**
	 * Tries to create a new Student object based on a line that is brought in from a file.
	 *
	 * @param line the current line of the file
	 * @return the Student object if one was created
	 */
	private static Student processStudent(String line) {
		Student s = null;
		Scanner sc = new Scanner(line);
		sc.useDelimiter(",");
		String[] elements = new String[6];
		int i = 0;
		try {
			while (sc.hasNext()) {
				elements[i] = sc.next();
				i++;
			}
			s = new Student(elements[0], elements[1], elements[2], elements[3], elements[4], Integer.parseInt(elements[5]));
		} catch (NullPointerException e) {
			sc.close();
			throw new IllegalArgumentException();
		}
		sc.close();
		return s;
	}
	
	/**
	 * Writes Student object information to a new file .
	 *
	 * @param fileName the file name of the new file
	 * @param studentDirectory the array of current students in the current directory
	 * @throws IOException if there is an issue writing to the file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = null;
		try {
			fileWriter = new PrintStream(new File(fileName));
		} catch (IOException e) {
			throw new IOException(fileName + " (Permission denied)");
		}

		for (int i = 0; i < studentDirectory.size(); i++) {
		    fileWriter.println(studentDirectory.get(i).toString());
		}

		fileWriter.close();
	}
}
