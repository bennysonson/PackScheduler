package edu.ncsu.csc216.pack_scheduler.course;



/**
 * Abstract superclass for Course and Event classes
 * @author CameronDutra
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days. */
	private String meetingDays;
	/** Course's starting time. */
	private int startTime;
	/** Course's ending time. */
	private int endTime;
	/** Maximum about of time of a Course*/
	public static final int UPPER_TIME = 2400;
	/** Maximum hour of a Course*/
	public static final int UPPER_HOUR = 60;

	/**
	 * Constructor for the Activity superclass
	 * @param title the Activity's title
	 * @param meetingDays the Activity's meeting days
	 * @param startTime the Activity's start time
	 * @param endTime the Activity's end time
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}


	/**
	 * Returns the Course's title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title only if title is a populated string
	 * and is not null
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is left empty or null.
	 */
	public void setTitle(String title) {
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException("Invalid course title");
		}
		this.title = title;
	}

	/**
	 * Returns the Course's meetingDays
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the Course's meetingDays
	 * @param meetingDays the meetingDays to set
	 */
	public void setMeetingDays(String meetingDays) {
		this.meetingDays = meetingDays;
	}

	/**
	 * Returns the Course's startTime
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's endTime
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Set the Course's start and end time at the same time
	 * @param startTime the Course's start time
	 * @param endTime the Course's end time
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (startTime < 0 || startTime > 2359) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		if (endTime < 0 || endTime > 2359) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		
		if (startTime % 100 > 59 || endTime % 100 > 59 ) {
			throw new IllegalArgumentException("Invalid meeting times");
		}
		if (getMeetingDays().equals("A") && (startTime != 0 || endTime != 0)){
			throw new IllegalArgumentException("Invalid meeting times");
		}
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Generates the string version of the entire meeting
	 * @return the String version of the meeting
	 */
	public String getMeetingString() {
		if (meetingDays.equals("A")) {
			return "Arranged";
		}
		
		String meetingString = meetingDays + " ";
		boolean pmStart = false;
		int startHour;
		int startMin;
		boolean pmEnd = false;
		int endHour;
		int endMin;
		
		if (startTime >= 1200) {
			pmStart = true;
		}
		if (startTime >= 1300) {
			startHour = (startTime - 1200) / 100;
		} else {
			startHour = startTime / 100;
		}
		startMin = startTime % 100;
		meetingString += startHour + ":";
		if (startMin == 0) {
			meetingString += "00";
		} else {
			meetingString += startMin;
		}
		if (pmStart) {
			meetingString += "PM-";
		} else {
			meetingString += "AM-";
		}
		
		if (endTime >= 1200) {
			pmEnd = true;
		}
		if (endTime >= 1300) {
			endHour = (endTime - 1200) / 100;
		} else {
			endHour = endTime / 100;
		}
		endMin = endTime % 100;
		meetingString += endHour + ":";
		if (endMin == 0) {
			meetingString += "00";
		} else {
			meetingString += endMin;
		}
		if (pmEnd) {
			meetingString += "PM";
		} else {
			meetingString += "AM";
		}
		
		return meetingString;
	}

	/**
	 * Generates a hash code for the Object
	 * @return the hash code for the Object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Determines equality of two Activity objects
	 * @return whether or not the two objects are equivalent
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	/**
	 * Returns an array of length 4 containing the Course name, section, title,
	 * and meeting days string
	 * @return the array containing the information
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Returns an array of length 7 containing the Course name, section, title,
	 * credits, instructorId, meeting days string, and an empty string
	 * @return the array containing the information
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Determines if two Activities whether they are Events or Courses are duplicates of one another
	 * @param activity the Activity you're testing against
	 * @return whether or not they are a duplicate of one another
	 */
	public abstract boolean isDuplicate(Activity activity);

    /**
     * Tests to see if there are conflicting days between the meetingDays of the two Activity objects
     * If so, checks to see if end time of one of the Activities conflicts with the start time of the other
     * whether that being one comes before the other or they are equal to one another. This goes both ways and 
     * then finally the objects are checked to see if they start at the exact same time.
     * @param possiblyConflictingActivity the Activity that is being tested against
     * @throws ConflictException if a conflict is found between the two Activities
     */
	@Override
	public void checkConflict(Activity possiblyConflictingActivity) throws ConflictException {
        boolean same = false;
        for (int i = 0; i < getMeetingDays().length(); i++) {
            char c = getMeetingDays().charAt(i);
            for (int j = 0; j < possiblyConflictingActivity.getMeetingDays().length(); j++) {
                char h = possiblyConflictingActivity.getMeetingDays().charAt(j);
                if (c == h) {
                    same = true;
                }
            }
        }
        if (same && (getMeetingDays().contains("A") || possiblyConflictingActivity.getMeetingDays().contains("A"))) {
            return;
        }

        int start = getStartTime();
        int end = getEndTime();
        int s = possiblyConflictingActivity.getStartTime();
        int e = possiblyConflictingActivity.getEndTime();
        if (same) {

            if (start < s && end > s) {
                throw new ConflictException();
            } else if (end == s || start == s) {
                throw new ConflictException();
            } else if (s < start && e > start) {
                throw new ConflictException();
            } else if (e == start || s == start) {
                throw new ConflictException();

            }

        }
	}
}