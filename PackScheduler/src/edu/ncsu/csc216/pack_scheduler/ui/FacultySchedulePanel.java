package edu.ncsu.csc216.pack_scheduler.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Creates a user interface for a Faculty to view the Courses that they are teaching and which Students
 * are enrolled in the Course along with other Course details.
 * 
 * @author Benson Liu
 * @author John Preston
 */
public class FacultySchedulePanel extends JPanel {
	/** ID used for object serialization */
	private static final long serialVersionUID = 1L;
	/** the table of Courses the current user (Faculty) is teaching */
	private JTable tableSchedule;
	/** the table of Students that are enrolled in the selected Course in Faculty Schedule */
	private JTable tableRoll;
	/** TableModel for Faculty Schedule */
	private TableModel scheduleTableModel;
	/** TableModel for Course Roll */
	private TableModel rollTableModel;
	/** assists in bordering/styling the elements of the FacultySchedulePanel */
	private Border lowerEtched;
	/** a scrollable interface of the current user's (Faculty) schedule that the user can interact with */
	private JScrollPane scrollSchedule;
	/** a scrollable interface of the currently selected Course's roll that the user can interact with */
	private JScrollPane scrollRoll;
	/** a panel containing the details of the Course selected in the Faculty Schedule */
	private JPanel pnlCourseDetails;
	/** JLabel for labeling the name of a course */
	private JLabel lblNameTitle = new JLabel("Name:");
	/** JLabel for labeling the section of a course */
	private JLabel lblSectionTitle = new JLabel("Section:");
	/** JLabel for labeling the title of a course */
	private JLabel lblTitleTitle = new JLabel("Title:");
	/** JLabel for labeling the instructor of a course */
	private JLabel lblInstructorTitle = new JLabel("Instructor:");
	/** JLabel for labeling the credit weight of a course */
	private JLabel lblCreditsTitle = new JLabel("Credits:");
	/** JLabel for labeling the meeting time of a course */
	private JLabel lblMeetingTitle = new JLabel("Meeting:");
	/** JLabel for labeling the enrollment capacity of a course */
	private JLabel lblEnrollmentCapTitle = new JLabel("Enrollement Cap:");
	/** JLabel for labeling the number of open seats for a course */
	private JLabel lblOpenSeatsTitle = new JLabel("Open Seats:");
	/** JLabel for labeling the number of wait-list spots for a course */
	private JLabel lblWaitlistTitle = new JLabel("Waitlist:");
	/** JLabel for the name of a course */
	private JLabel lblName = new JLabel("");
	/** JLabel for the section of a course */
	private JLabel lblSection = new JLabel("");
	/** JLabel for the title of a course */
	private JLabel lblTitle = new JLabel("");
	/** JLabel for the instructor of a course */
	private JLabel lblInstructor = new JLabel("");
	/** JLabel for the credit weight of a course */
	private JLabel lblCredits = new JLabel("");
	/** JLabel for the meeting time of a course */
	private JLabel lblMeeting = new JLabel("");
	/** JLabel for the enrollment capacity of a course */
	private JLabel lblEnrollmentCap = new JLabel("");
	/** JLabel for the number of open seats for a course */
	private JLabel lblOpenSeats = new JLabel("");
	/** JLabel for the number of wait-list spots for a course */
	private JLabel lblWaitlist = new JLabel("");
	/** the schedule of the current user (Faculty) */
	private FacultySchedule schedule;
	/** the current user (Faculty) */
	private Faculty currentUser;
	/** the catalog of courses currently in the RegistrationManager */
	private CourseCatalog catalog;

	/**
	 * Constructs a FacultySchedulePanel Object that contains the Faculty Schedule, Course Details panel, and the Course
	 * Roll panel. The current user can interact with this panel by selecting a Course from the Faculty's Schedule, thus
	 * producing a detailed overview of the Course and which Students are enrolled in the Course.
	 */
	public FacultySchedulePanel() {
		super(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

		RegistrationManager manager = RegistrationManager.getInstance();
		currentUser = (Faculty) manager.getCurrentUser();
		catalog = manager.getCourseCatalog();

		
		// ** facultySchedule set up **
		scheduleTableModel = new TableModel(true);
		tableSchedule = new JTable(scheduleTableModel);
		tableSchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSchedule.setPreferredScrollableViewportSize(new Dimension(500, 500));
		tableSchedule.setFillsViewportHeight(true);
		tableSchedule.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				String name = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 0).toString();
				String section = tableSchedule.getValueAt(tableSchedule.getSelectedRow(), 1).toString();
				Course c = catalog.getCourseFromCatalog(name, section);
				updateCourseDetails(c);
				rollTableModel.updateRollData(c);
			}

		});
		scrollSchedule = new JScrollPane(tableSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollSchedule.setBorder(BorderFactory.createTitledBorder(lowerEtched, "Faculty Schedule"));
		
		
		// ** courseDetailsPanel set up **
		pnlCourseDetails = new JPanel();
		pnlCourseDetails.setLayout(new GridLayout(5, 1));
		JPanel pnlName = new JPanel(new GridLayout(1, 4));
		pnlName.add(lblNameTitle);
		pnlName.add(lblName);
		pnlName.add(lblSectionTitle);
		pnlName.add(lblSection);

		JPanel pnlTitle = new JPanel(new GridLayout(1, 1));
		pnlTitle.add(lblTitleTitle);
		pnlTitle.add(lblTitle);

		JPanel pnlInstructor = new JPanel(new GridLayout(1, 4));
		pnlInstructor.add(lblInstructorTitle);
		pnlInstructor.add(lblInstructor);
		pnlInstructor.add(lblCreditsTitle);
		pnlInstructor.add(lblCredits);

		JPanel pnlMeeting = new JPanel(new GridLayout(1, 1));
		pnlMeeting.add(lblMeetingTitle);
		pnlMeeting.add(lblMeeting);

		JPanel pnlEnrollment = new JPanel(new GridLayout(1, 6));
		pnlEnrollment.add(lblEnrollmentCapTitle);
		pnlEnrollment.add(lblEnrollmentCap);
		pnlEnrollment.add(lblOpenSeatsTitle);
		pnlEnrollment.add(lblOpenSeats);
		pnlEnrollment.add(lblWaitlistTitle);
		pnlEnrollment.add(lblWaitlist);

		pnlCourseDetails.add(pnlName);
		pnlCourseDetails.add(pnlTitle);
		pnlCourseDetails.add(pnlInstructor);
		pnlCourseDetails.add(pnlMeeting);
		pnlCourseDetails.add(pnlEnrollment);

		TitledBorder borderCourseDetails = BorderFactory.createTitledBorder(lowerEtched, "Course Details");
		pnlCourseDetails.setBorder(borderCourseDetails);

		
		// ** courseRollPanel set up **
		rollTableModel = new TableModel(false);
		tableRoll = new JTable(rollTableModel);
		tableRoll.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableRoll.setPreferredScrollableViewportSize(new Dimension(500, 500));
		tableRoll.setFillsViewportHeight(true);

		scrollRoll = new JScrollPane(tableRoll, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollRoll.setBorder(BorderFactory.createTitledBorder(lowerEtched, "Course Roll"));

		updateScheduleTable();

		
		// ** adding components to FacultySchedulePanel **
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.BOTH;
		add(scrollSchedule, gc);

		gc.gridy = 1;
		add(pnlCourseDetails, gc);

		gc.gridy = 2;
		add(scrollRoll, gc);
	}

	/**
	 * Updates the pnlCourseDetails with full information about the most recently
	 * selected course.
	 */
	private void updateCourseDetails(Course c) {
		if (c != null) {
			lblName.setText(c.getName());
			lblSection.setText(c.getSection());
			lblTitle.setText(c.getTitle());
			lblInstructor.setText(c.getInstructorId());
			lblCredits.setText("" + c.getCredits());
			lblMeeting.setText(c.getMeetingString());
			lblEnrollmentCap.setText("" + c.getCourseRoll().getEnrollmentCap());
			lblOpenSeats.setText("" + c.getCourseRoll().getOpenSeats());
			lblWaitlist.setText("" + c.getCourseRoll().getNumberOnWaitlist());
		}
	}
	
	/**
	 * Resets the Course Details panel to its blank state.
	 */
	private void resetCourseDetails() {
		lblName.setText("");
		lblSection.setText("");
		lblTitle.setText("");
		lblInstructor.setText("");
		lblCredits.setText("");
		lblMeeting.setText("");
		lblEnrollmentCap.setText("");
		lblOpenSeats.setText("");
		lblWaitlist.setText("");
	}

	/**
	 * Updates the Faculty Schedule table.
	 */
	public void updateScheduleTable() {
		scheduleTableModel.updateScheduleData();
	}
	
	/**
	 * Clears the Course Details panel and Course Roll Table as well as the row selection for both tables upon logout.
	 */
	public void clearSelection() {
		tableSchedule.clearSelection();
		tableRoll.clearSelection();
		rollTableModel.resetData();
		resetCourseDetails();
	}

	/**
	 * The TableMode is the object underlying the JTable object that displays the list of Objects to the user. This
	 * design was heavily inspired by StudentRegistrationPanel$CourseTableModel.
	 * 
	 * @author Benson Liu
	 * @author John Preston
	 */
	private class TableModel extends AbstractTableModel {

		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Column names if isFacultySchedule */
		private final String[] facultyScheduleColumns = { "Name", "Section", "Title", "Meeting Days", "Open Seats" };
		/** Column names if not isFacultySchedule */
		private final String[] courseRollColumns = { "First Name", "Last Name", "Student ID" };
		/** Column names for the table */
		private String[] columnNames;
		/** Data stored in the table */
		private Object[][] data;
		/** Boolean flag if the model applies to the facultySchedule or the courseRoll */
		private boolean isFacultySchedule;

		/**
		 * Constructs the TableModel by requesting the latest information using the updateData() method.
		 */
		public TableModel(boolean isFacultySchedule) {
			this.isFacultySchedule = isFacultySchedule;
			if (this.isFacultySchedule) {
				columnNames = facultyScheduleColumns;
			} else {
				columnNames = courseRollColumns;
			}
			updateScheduleData();
		}

		/**
		 * Returns the number of columns in the table.
		 * 
		 * @return the number of columns in the table.
		 */
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * Returns the number of rows in the table.
		 * 
		 * @return the number of rows in the table.
		 */
		public int getRowCount() {
			if (data == null)
				return 0;
			return data.length;
		}

		/**
		 * Returns the column name at the given index.
		 * 
		 * @return the column name at the given column.
		 */
		public String getColumnName(int col) {
			return columnNames[col];
		}

		/**
		 * Returns the data at the given (row, col) index.
		 * 
		 * @return the data at the given location.
		 */
		public Object getValueAt(int row, int col) {
			if (data == null)
				return null;
			return data[row][col];
		}

		/**
		 * Sets the given value to the given (row, col) location.
		 * 
		 * @param value  Object to modify in the data.
		 * @param row    location to modify the data.
		 * @param column location to modify the data.
		 */
		public void setValueAt(Object value, int row, int col) {
			if (data != null) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
		}
		
		/**
		 * Clears any data that was in the table and updates the display.
		 */
		private void resetData() {
			data = null;
			FacultySchedulePanel.this.repaint();
			FacultySchedulePanel.this.validate();
		}

		/**
		 * Updates the given model with information from the FacultySchedule.
		 */
		private void updateScheduleData() {
			currentUser = (Faculty) RegistrationManager.getInstance().getCurrentUser();
			if (currentUser != null) {
				schedule = currentUser.getSchedule();
				data = schedule.getScheduledCourses();
				FacultySchedulePanel.this.repaint();
				FacultySchedulePanel.this.validate();
			}
		}

		/**
		 * Updates rollTableModel with information from the given Course.
		 * @param c the Course selected in the FacultySchedule table.
		 */
		private void updateRollData(Course c) {
			if (currentUser != null) {
				data = c.getCourseRoll().getRoll();
				FacultySchedulePanel.this.repaint();
				FacultySchedulePanel.this.validate();
			}
		}
	}
}
