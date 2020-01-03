package edu.ncsu.csc216.pack_scheduler.user;

/**
 * methods for the abstract class User
 * @author Cameron, Benson, Luis
 *
 */
public abstract class User {

	/**  Student's first name. */
	private String firstName;
	/**  Student's last name. */
	private String lastName;
	/**  Student's id. */
	private String id;
	/**  Student's email. */
	private String email;
	/**  Student's password. */
	private String password;

	/**
	 * Constructor for the user object
	 * @param firstName the first name of the user	
	 * @param lastName the last name of the user
	 * @param id the id of the user
	 * @param email the email of the user
	 * @param password the password of the user
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Returns the Student's first name.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the Student's first name.
	 *
	 * @param firstName the Student's first name
	 * @throws IllegalArgumentException if name is null or empty
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.equals("")) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Returns the Student's last name.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the Student's last name.
	 *
	 * @param lastName the Student's last name
	 * @throws IllegalArgumentException if last name is null or empty
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.equals("")) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Returns the Student's id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the Student's id.
	 *
	 * @param id the Student's id
	 * @throws IllegalArgumentException if id is null or empty
	 */
	protected void setId(String id) {
		if (id == null || id.equals("")) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Returns the Student's email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the Student's email address.
	 *
	 * @param email the email to set
	 * @throws IllegalArgumentException if email is null, is empty, does not
	 *                                  contain @, and if the . in the address comes
	 *                                  before the @.
	 */
	public void setEmail(String email) {
		if (email == null || email.equals("") || !email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (email.lastIndexOf('.') < email.indexOf('@')) {
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Returns the Student's password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the Student's password.
	 *
	 * @param password the Student's password
	 * @throws IllegalArgumentException if password is null or empty
	 */
	public void setPassword(String password) {
		if (password == null || password.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}