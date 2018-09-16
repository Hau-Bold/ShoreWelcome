package Intersport.Intersport;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import time.DayAndMonth;
import time.Time;
import utils.Appointment;
import utils.Utils;

/**
 * Unit test for the class Utils.
 */
public class UtilsTest {

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
	private static List<Appointment> lstAppointments = new ArrayList<Appointment>();

	/** Appointments in December */
	static Appointment decemberMonDay = new Appointment();
	static Appointment decemberTuesDay = new Appointment();
	static Appointment decemberWednesDay = new Appointment();

	/** Appointments in January */
	static Appointment januarySaturDay = new Appointment();
	static Appointment januarySunDay = new Appointment();
	static Appointment januaryMonday = new Appointment();

	/** dates in December */
	static time.Date decemberMonDayDate;
	static time.Date decemberTuesDayDate;
	static time.Date decemberWednesDayDate;

	/** dates in January */
	static time.Date januaryThursDayDate;
	static time.Date januaryFriDayDate;
	static time.Date januarySaturdayDate;

	@BeforeClass
	public static void initAppointments() {

		decemberMonDayDate = new time.Date(DayOfWeek.MONDAY, 29, Month.DECEMBER, 2017);
		decemberTuesDayDate = new time.Date(DayOfWeek.TUESDAY, 30, Month.DECEMBER, 2017);
		decemberWednesDayDate = new time.Date(DayOfWeek.WEDNESDAY, 31, Month.DECEMBER, 2017);

		januaryThursDayDate = new time.Date(DayOfWeek.THURSDAY, 1, Month.JANUARY, 2018);
		januaryFriDayDate = new time.Date(DayOfWeek.FRIDAY, 2, Month.JANUARY, 2018);
		januarySaturdayDate = new time.Date(DayOfWeek.SATURDAY, 3, Month.JANUARY, 2018);

		decemberMonDay.setDate(decemberWednesDayDate);
		decemberTuesDay.setDate(decemberTuesDayDate);
		decemberWednesDay.setDate(decemberWednesDayDate);
		januarySaturDay.setDate(januaryThursDayDate);
		januarySunDay.setDate(januaryFriDayDate);
		januaryMonday.setDate(januarySaturdayDate);

		lstAppointments.add(decemberMonDay);
		lstAppointments.add(decemberTuesDay);
		lstAppointments.add(decemberWednesDay);

		lstAppointments.add(januarySaturDay);
		lstAppointments.add(januarySunDay);
		lstAppointments.add(januaryMonday);

	}

	/**
	 * tests the Util's method "extractTime"
	 */
	@Test
	public void extractTimeTest() {
		String dateToTestAsString = "20180109T103000";
		Date dateToTest = null;

		try {
			dateToTest = simpleDateFormat.parse(dateToTestAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Time actual = Utils.extractTime(dateToTest);
		Time expected = new Time(10, 30, 00);

		assertEquals(expected, actual);

	}

	/**
	 * tests the Util's method "extractDatwe"
	 */
	@Test
	public void extractDateTest() {
		String dateToTestAsString = "20180109T103000";
		Date dateToTest = null;

		try {
			dateToTest = simpleDateFormat.parse(dateToTestAsString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		time.Date actual = Utils.extractDate(dateToTest);
		time.Date expected = new time.Date(DayOfWeek.TUESDAY, 9, Month.JANUARY, 2018);

		assertEquals(expected, actual);

	}

	/**
	 * tests the method "removeAppointmentsNotBelongingToCurrentWeek"
	 */
	@Test
	public void removeAppointmentsNotBelongingToCurrentWeekTest() {

		DayAndMonth monday = new DayAndMonth(29, 12);
		DayAndMonth saturday = new DayAndMonth(3, 12);

		List<Appointment> expected = lstAppointments;
		List<Appointment> actual = Utils.removeAppointmentsNotBelongingToCurrentWeek(lstAppointments, monday, saturday);

		assertEquals(expected, actual);

	}

	/**
	 * tests the method "removeAppointmentsNotBelongingToCurrentWeek" with empty
	 * list:
	 */
	@Test
	public void removeAppointmentsNotBelongingToCurrentWeekEmptyTest() {

		DayAndMonth monday = new DayAndMonth(29, 12);
		DayAndMonth saturday = new DayAndMonth(3, 12);

		List<Appointment> expected = Collections.emptyList();
		List<Appointment> actual = Utils.removeAppointmentsNotBelongingToCurrentWeek(null, monday, saturday);

		assertEquals(expected, actual);

	}

}
