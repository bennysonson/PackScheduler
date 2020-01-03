/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;



/**
 * Test class to ensure correct implementation of the Activity class
 * as far as determining conflicts between Activities.
 * @author CameronDutra
 *
 */
public class ActivityTest {
    /** Course name */
    private static final String NAME = "CSC216";
    /** Course title */
    private static final String TITLE = "Programming Concepts - Java";
    /** Course section */
    private static final String SECTION = "001";
    /** Course credits */
    private static final int CREDITS = 4;
    /** Course instructor id */
    private static final String INSTRUCTOR_ID = "sesmith5";
    /** Course meeting days */
    private static final String MEETING_DAYS = "MW";
    /** Course start time */
    private static final int START_TIME = 1330;
    /** Course end time */
    private static final int END_TIME = 1445;
    /** Course enrollment Cap */
	private static final int ENROLLMENT_CAP = 100;
	/**
	 * Test method for checkConflict.
	 */
	@Test
	public void testCheckConflict() {
	    Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP, "MW", 1330, 1445);
	    Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", ENROLLMENT_CAP, "TH", 1330, 1445);
	    try {
	        a1.checkConflict(a2);
	        assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
	        assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM", a2.getMeetingString());
	    } catch (ConflictException e) {
	        fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
	    }
	    
	    //Switch the two Courses to ensure commutativity
	    try {
	        a2.checkConflict(a1);
	        assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
	        assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM", a2.getMeetingString());
	    } catch (ConflictException e) {
	        fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
	    }
	    
	  //Update a1 with the same meeting days and a start time that overlaps the end time of a2
	    a1.setMeetingDays("TH");
	    a1.setActivityTime(1445, 1530);
	    try {
	        a1.checkConflict(a2);
	        fail(); //ConflictException should have been thrown, but was not.
	    } catch (ConflictException e) {
	        //Check that the internal state didn't change during method call.
	        assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
	        assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
	    }
	    
	    //Set both to the exact same start and end time
	    a2.setMeetingDays("T");
	    a2.setActivityTime(1445, 1530);
	    try {
	    	a1.checkConflict(a2);
	    	fail();
	    } catch (ConflictException e) {
	    	assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
	    	assertEquals("T 2:45PM-3:30PM", a2.getMeetingString());
	    }
	}
	/**
     * Check set activity time.
     */
    @Test
    public void checkSetActivityTime() {
        Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        assertEquals(TITLE, c.getTitle());

        assertEquals(MEETING_DAYS, c.getMeetingDays());
        assertEquals(START_TIME, c.getStartTime());
        assertEquals(END_TIME, c.getEndTime());

        // Test that setting the start time to 2400 doesn't change the start time (or
        // anything else).
        try {
            c.setActivityTime(2400, 1445);
            fail();
        } catch (IllegalArgumentException e) {

            assertEquals(TITLE, c.getTitle());

            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }

        // Test that setting the start time to 1360 doesn't change the start time (or
        // anything else).
        try {
            c.setActivityTime(1360, 1445);
            fail();
        } catch (IllegalArgumentException e) {

            assertEquals(TITLE, c.getTitle());

            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }

        // Test that setting the start time to -1 doesn't change the start time (or
        // anything else).
        try {
            c.setActivityTime(-1, 1445);
            fail();
        } catch (IllegalArgumentException e) {

            assertEquals(TITLE, c.getTitle());

            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }

        // Test that setting the start time to 2400 doesn't change the start time (or
        // anything else).
        try {
            c.setActivityTime(1330, 2400);
            fail();
        } catch (IllegalArgumentException e) {

            assertEquals(TITLE, c.getTitle());

            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }

        // Test that setting the start time to 1360 doesn't change the start time (or
        // anything else).
        try {
            c.setActivityTime(1330, 1360);
            fail();
        } catch (IllegalArgumentException e) {

            assertEquals(TITLE, c.getTitle());

            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }

        // Test that setting the start time to -1 doesn't change the start time (or
        // anything else).
        try {
            c.setActivityTime(1330, -1);
            fail();
        } catch (IllegalArgumentException e) {

            assertEquals(TITLE, c.getTitle());

            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }

        // Test that having the start time after the end time doesn't change the values.
        try {
            c.setActivityTime(1445, 1330);
            fail();
        } catch (IllegalArgumentException e) {

            assertEquals(TITLE, c.getTitle());

            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }

        // Valid set of start time
        c.setActivityTime(1350, 1445);

        assertEquals(TITLE, c.getTitle());

        assertEquals(MEETING_DAYS, c.getMeetingDays());
        assertEquals(1350, c.getStartTime());
        assertEquals(END_TIME, c.getEndTime());

        // Valid set of end time
        c.setActivityTime(1350, 1526);

        assertEquals(TITLE, c.getTitle());

        assertEquals(MEETING_DAYS, c.getMeetingDays());
        assertEquals(1350, c.getStartTime());
        assertEquals(1526, c.getEndTime());

    }
    /**
     * Tests setTitle().
     */
    @Test
    public void testSetTitle() {
        Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        
        assertEquals(TITLE, c.getTitle());
      
        assertEquals(MEETING_DAYS, c.getMeetingDays());
        assertEquals(START_TIME, c.getStartTime());
        assertEquals(END_TIME, c.getEndTime());
        
        //Test that setting the title to null doesn't change the title (or anything else).
        try {
            c.setTitle(null);
            fail();
        } catch (IllegalArgumentException e) {
            
            assertEquals(TITLE, c.getTitle());
          
            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }
        
        //Test that setting the title to "" doesn't change the title (or anything else).
        try {
            c.setTitle("");
            fail();
        } catch (IllegalArgumentException e) {
           
            assertEquals(TITLE, c.getTitle());
          
            assertEquals(MEETING_DAYS, c.getMeetingDays());
            assertEquals(START_TIME, c.getStartTime());
            assertEquals(END_TIME, c.getEndTime());
        }
        
        //Valid set
        c.setTitle("A new title");
       
        assertEquals("A new title", c.getTitle());
    
        assertEquals(MEETING_DAYS, c.getMeetingDays());
        assertEquals(START_TIME, c.getStartTime());
        assertEquals(END_TIME, c.getEndTime());
    }
    
    /**
     * Test hash code.
     */
    @Test
    public void testHashCode() {
        Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c3 = new Course(NAME, "Different", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c4 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c5 = new Course(NAME, TITLE, SECTION, 5, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c6 = new Course(NAME, TITLE, SECTION, CREDITS, "Different", ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c7 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "TH", START_TIME, END_TIME);
        Activity c8 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 830, END_TIME);
        Activity c9 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, 1400);
        
        //Test for the same hash code for the same values
        assertEquals(c1.hashCode(), c2.hashCode());
        
        //Test for each of the fields
        assertNotEquals(c1.hashCode(), c3.hashCode());
        assertNotEquals(c1.hashCode(), c4.hashCode());
        assertNotEquals(c1.hashCode(), c5.hashCode());
        assertNotEquals(c1.hashCode(), c6.hashCode());
        assertNotEquals(c1.hashCode(), c7.hashCode());
        assertNotEquals(c1.hashCode(), c8.hashCode());
        assertNotEquals(c1.hashCode(), c9.hashCode());
    }
    
    /**
     * Test equals object.
     */
    @Test
    public void testEqualsObject() {
        Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c3 = new Course(NAME, "Different", SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c4 = new Course(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c5 = new Course(NAME, TITLE, SECTION, 5, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c6 = new Course(NAME, TITLE, SECTION, CREDITS, "Different", ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
        Activity c7 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "TH", START_TIME, END_TIME);
        Activity c8 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 830, END_TIME);
        Activity c9 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, 1400);
        
        //Test for equality in both directions
        assertTrue(c1.equals(c2));
        assertTrue(c2.equals(c1));
        
        //Test for each of the fields
        assertFalse(c1.equals(c3));
        assertFalse(c1.equals(c4));
        assertFalse(c1.equals(c5));
        assertFalse(c1.equals(c6));
        assertFalse(c1.equals(c7));
        assertFalse(c1.equals(c8));
        assertFalse(c1.equals(c9));
    }
    /**
     * Tests that getMeetingString() works correctly
     */
    @Test
    public void testGetMeetingString() {
        //The code below is commented out until you make some changes to Course.
        //Once those are made, remove the line of code fail() and uncomment the provided tests.

        
      Activity c1 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
      assertEquals("MW 1:30PM-2:45PM", c1.getMeetingString());
      Activity c2 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, 900, 1035);
      assertEquals("MW 9:00AM-10:35AM", c2.getMeetingString());
      Activity c3 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "A");
      assertEquals("Arranged", c3.getMeetingString());
      Activity c4 = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, "TH", 1145, 1425);
      assertEquals("TH 11:45AM-2:25PM", c4.getMeetingString());
    }

}