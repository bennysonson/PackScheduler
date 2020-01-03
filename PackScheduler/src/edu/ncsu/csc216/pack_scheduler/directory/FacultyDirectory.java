package edu.ncsu.csc216.pack_scheduler.directory;

import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * The purpose of the FacultyDirectory class is to maintain a directory of all Faculty enrolled
 * at NC State. The FacultyDirectory object has the ability to load Faculty in from a file and
 * the ability to add or remove Faculty from said directory. There are also methods implemented
 * to return the entire Faculty directory as well as save the current directory to a file. The
 * FacultyDirectory object will be used in multiple other parts of the program to retrieve info
 * on the Faculty currently enrolled.
 * 
 * @author John Preston
 */
public class FacultyDirectory {
	/** list of Faculty in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Creates an empty faculty directory.
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Creates an empty faculty directory. All faculty in the previous list are removed unless saved by the user.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}
	
	/**
	 * Constructs the faculty directory by reading in faculty information from the given file.
	 * 
	 * @param  fileName file containing list of faculty
	 * @throws IllegalArgumentException if the file cannot be found
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	
	/**
	 * Adds a Faculty to the directory. Returns true if the Faculty is added, false otherwise.
	 * 
	 * This method also hashes the Faculty's password for internal storage.
	 * 
	 * @param  firstName Faculty's first name
	 * @param  lastName Faculty's last name
	 * @param  id Faculty's id
	 * @param  email Faculty's email
	 * @param  password Faculty's password
	 * @param  repeatPassword Faculty's repeated password
	 * @param  maxCourses the maximum number of courses a Faculty can teach
	 * @return true if added
	 * @throws IllegalArgumentException when password is invalid, cannot be hashed, or does not match
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, 
			String password, String repeatPassword, int maxCourses ) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());

			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPassword.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}
		
		//If an IllegalArgumentException is thrown, it's passed up from Faculty to the GUI
		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			if (f.getId().equals(faculty.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
	}
	
	/**
	 * Removes the Faculty with the given id from the list of Faculty with the given id.
	 * Returns true if the Faculty is removed and false if the Faculty is not in the list.
	 * 
	 * @param facultyId faculty's id
	 * @return true if removed, false otherwise
	 */
	public boolean removeFaculty(String facultyId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			if (s.getId().equals(facultyId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns all faculty in the directory with a column for first name, last name, and id.
	 * @return String array containing faculty's first name, last name, and id
	 */
	public String[][] getFacultyDirectory() {
		String [][] directory = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			directory[i][0] = s.getFirstName();
			directory[i][1] = s.getLastName();
			directory[i][2] = s.getId();
		}
		return directory;
	}
	
	/**
	 * Saves all faculty in the directory to a file.
	 * @param fileName name of file to save faculty to
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}
	
	/**
	 * Returns a Faculty by the provided id.
	 * @param id the id to be searched for
	 * @return the Faculty with the id
	 */
	public Faculty getFacultyById(String id) {
		for(int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equals(id)) {
				return facultyDirectory.get(i);
			}
		}
		return null;
	}
}
