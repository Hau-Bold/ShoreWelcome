package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import time.DayAndMonth;
import time.Time;

public class Utils {

	private static long lastModified = 0;
	private static long lastModifiedTemp;

	/**
	 * yields a list of synchronized *.ics files and sets the value of folderchanged
	 * of FilesOfFolder in dependency of content of directory has changed. It
	 * Compares the current hour and current minute and sets the variable so that a
	 * refresh of the page can be done in the morning
	 *
	 * 
	 * @param directory
	 *            - the directory of the ics files
	 * @param currentMinute
	 *            - the current minute
	 * @param currentHour
	 *            - the current hour
	 * @return instance of FilesOfFolder: list of the *.ics files, record whether
	 *         folder changed (var is true if a new Appointment has been listed)
	 * @throws IOException
	 *             - in case of technical error
	 */
	public static FilesOfFolder getICSFilesFromDirectory(String directory, int currentHour, int currentMinute)
			throws IOException {

		Boolean folderChanged = Boolean.FALSE;
		File folder = new File(directory);

		List<File> lstAppointment = null;

		if (folder.exists()) {

			lstAppointment = Arrays.asList(folder.listFiles());

			lastModifiedTemp = folder.getCanonicalFile().lastModified();

			if (Long.compare(lastModifiedTemp, lastModified) != 0) {
				/** the folder has changed */
				lastModified = lastModifiedTemp;
				folderChanged = Boolean.TRUE;
			} else {

				if (Integer.compare(currentHour, 7) == 0) {

					if ((0 <= currentMinute) && (currentMinute <= 10)) {
						lastModified = 0;
					}

				}
			}
		}

		return new FilesOfFolder(lstAppointment, folderChanged);
	}

	/**
	 * generates an appointment form the received *.ics file
	 * 
	 * @param file
	 *            - the *.ics file
	 * @return - an appointment
	 * @throws IOException
	 *             - in case of technical error
	 * @throws ParseException
	 *             - in case of technical error
	 */
	public static Appointment generateAppointmentFromFile(File file) throws IOException, ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.GERMAN);
		Appointment appointment = new Appointment();
		StringBuilder builder = new StringBuilder();
		Boolean isReaderInLineOfDescription = Boolean.FALSE;

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		String line;
		while ((line = bufferedReader.readLine()) != null) {

			if (!isReaderInLineOfDescription) {
				if (line.startsWith(Constants.DTSTART)) {

					int indexOfColon = line.indexOf(Constants.COLON);

					String startDateAsString = line.substring(indexOfColon + 1, line.length());
					Date startDate = simpleDateFormat.parse(startDateAsString);

					/** setting start time */
					appointment.setStartTime(extractTime(startDate));
					/** setting day of appointment */
					appointment.setDate(extractDate(startDate));
				}

				else if (line.startsWith(Constants.DTEND)) {
					int indexOfColon = line.indexOf(Constants.COLON);

					String endDateAsString = line.substring(indexOfColon + 1, line.length());
					Date endDate = simpleDateFormat.parse(endDateAsString);

					/** setting end time */
					appointment.setEndTime(extractTime(endDate));

				} else if (line.startsWith(Constants.DESCRIPTION)) {
					isReaderInLineOfDescription = Boolean.TRUE;
				}
			}

			else {
				/** reader is in or after line starting with description */

				if (line.startsWith(Constants.SUMMARY)) {
					break;
				}
				builder.append(line.trim());
			}

		}

		bufferedReader.close();

		String descriptionAsString = builder.toString();
		String[] descriptionSplittet = descriptionAsString.split("\\\\n");
		String subject = extractIdentifier(descriptionSplittet, Constants.SUBJECT);
		String employee = extractIdentifier(descriptionSplittet, Constants.EMPLOYEE);
		String customer = extractIdentifier(descriptionSplittet, Constants.CUSTOMER);
		String service = extractIdentifier(descriptionSplittet, Constants.SERVICE);

		appointment.setSubject(subject);
		appointment.setEmployee(employee);
		if (customer != null) {
			appointment.setCustomer(handleCustomer(customer));
		}
		appointment.setService(handleService(service));

		return appointment;
	}

	/**
	 * handles the service
	 * 
	 * @param service
	 *            - the service
	 * @return the handled service
	 */
	private static String handleService(String service) {

		if (service != null) {
			if (service.contains("(")) {

				int indexOfLeftParenthesis = service.indexOf("(");

				return service.substring(0, indexOfLeftParenthesis - 1).trim();

			}
		}
		return service;
	}

	/**
	 * handles the customer
	 * 
	 * @param customer
	 *            - the customer
	 * @return the handled customer
	 */
	// private static Customer handleCustomer(String customer) {
	//
	// Customer response = new Customer();
	//
	// if (customer != null) {
	// customer = customer.replace(" ", " ");
	//
	// if (customer.contains("(")) {
	//
	// /** remove unnecessary information */
	// int indexOfLeftParenthesis = customer.indexOf("(");
	// customer = customer.substring(0, indexOfLeftParenthesis).trim();
	// }
	//
	// String[] customerSplitted = customer.split(" ");
	//
	// if (Integer.compare(customerSplitted.length, 3) == 0) {
	// /** Salutation is contained */
	// response.setSalutation(customerSplitted[0]);
	// response.setPreName(customerSplitted[1].substring(0, 1).toUpperCase() + ".");
	// }
	//
	// else if (Integer.compare(customerSplitted.length, 2) == 0) {
	// /** No Salutation is contained */
	// String preName = customerSplitted[0];
	//
	// if (!(preName == Constants.SALUTATION_MALE) || !(preName ==
	// Constants.SALUTATION_FEMALE)) {
	// response.setPreName(customerSplitted[0].substring(0, 1) + ".");
	// } else {
	// response.setPreName(preName);
	// }
	// }
	//
	// response.setSurName(customerSplitted[customerSplitted.length - 1]);
	// }
	// return response;
	// }

	public static Customer handleCustomer(String customer) {

		Customer response = new Customer();

		if (customer != null) {
			customer = customer.replace("  ", " ");

			if (customer.contains("(")) {

				/** remove unnecessary information */
				int indexOfLeftParenthesis = customer.indexOf("(");
				customer = customer.substring(0, indexOfLeftParenthesis).trim();
			}

			String[] customerSplitted = customer.split(" ");

			if (customerSplitted.length >= 3) {
				/** Salutation is contained ? */

				String salutation = customerSplitted[0].trim();

				StringBuilder builder = new StringBuilder();
				if (salutation.equals(Constants.SALUTATION_MALE) || salutation.equals(Constants.SALUTATION_FEMALE)) {
					/** salutation is a salutation! */
					response.setSalutation(salutation);

					for (int position = 1; position < customerSplitted.length - 1; position++) {
						builder.append(customerSplitted[position]);
						builder.append(Constants.EMPTY_SPACE);
					}

				} else {

					/** salutation is not a salutation! */

					for (int position = 0; position < customerSplitted.length - 1; position++) {
						builder.append(customerSplitted[position]);
						builder.append(Constants.EMPTY_SPACE);
					}
				}
				response.setPreName(builder.toString().trim());
			}

			else if (Integer.compare(customerSplitted.length, 2) == 0) {
				/** No Salutation is contained */
				String preName = customerSplitted[0];

				if (!(preName == Constants.SALUTATION_MALE) || !(preName == Constants.SALUTATION_FEMALE)) {
					response.setPreName(customerSplitted[0]);
				} else {
					response.setPreName(preName);
				}
			}

			if (customerSplitted.length > 1) {
				response.setCustomerName(customerSplitted[customerSplitted.length - 1].trim().substring(0, 1) + ".");
			}
		}
		return response;
	}

	/**
	 * extracts the encapsulated time from the received date
	 * 
	 * @param date
	 *            - the received date
	 * @return the encapsulated time
	 */
	public static Time extractTime(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return new Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND));
	}

	/**
	 * extracts the encapsulated time.Date from the received date
	 * 
	 * @param date
	 *            - the received date
	 * @return the encapsulated time.Date
	 */
	public static time.Date extractDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		DayOfWeek dayOfWeek = localDate.getDayOfWeek();
		int dayOfMonth = localDate.getDayOfMonth();
		Month month = localDate.getMonth();
		int year = localDate.getYear();

		return new time.Date(dayOfWeek, dayOfMonth, month, year);
	}

	/**
	 * extracts the value corresponding to an identifier from the receiving.
	 * 
	 * @param receiving
	 *            - StringArray
	 * @param identifier
	 *            - the identifier
	 * @return the value
	 */
	private static String extractIdentifier(String[] receiving, String identifier) {

		String result = null;

		for (String content : receiving) {
			if (content.contains(identifier)) {
				int indexOfColon = content.indexOf(Constants.COLON);
				result = content.substring(indexOfColon + 1, content.length());
			}
		}

		if (result != null) {
			return result.trim();
		}
		return result;
	}

	public static List<Appointment> removeAppointmentsNotBelongingToCurrentWeek(List<Appointment> receiving,
			DayAndMonth monday, DayAndMonth saturday) {

		if ((receiving == null) || receiving.isEmpty()) {
			return Collections.emptyList();
		}

		/** computing date of Monday in current week */
		int dayOfMonday = monday.getDay();
		int monthOfMonday = monday.getMonth();
		int dayOfSaturday = saturday.getDay();
		int monthOfSaturday = saturday.getMonth();

		Iterator<Appointment> iterator = receiving.iterator();
		Boolean isWholeCurrentWeekInSameYear = monthOfMonday <= monthOfSaturday;

		while (iterator.hasNext()) {
			Appointment current = iterator.next();
			time.Date currentDate = current.getDate();
			int currentMonth = currentDate.getMonth().getValue() - 1;
			int currentDay = currentDate.getDayOfMonth();

			if (isWholeCurrentWeekInSameYear) {
				/** case No Filtering Across Annual Limits: */

				/** case: same month */
				if (Integer.compare(monthOfMonday, monthOfSaturday) == 0) {

					// Appointment not in period
					if (Integer.compare(currentMonth, monthOfMonday) == 0) {

						/** appointment in current month */

						if ((currentDay < dayOfMonday) || (currentDay > dayOfSaturday)) {
							iterator.remove();
						}

					} else {
						iterator.remove();
					}

				}
				/** case: month of Saturday is after month of Monday */
				else if (Integer.compare(monthOfMonday, monthOfSaturday - 1) == 0) {
					// Appointment lies in month of Monday
					if (Integer.compare(monthOfMonday, currentMonth) == 0) {

						// Appointment not in period
						if (dayOfMonday > currentDay) {
							iterator.remove();
						}
					}

					// Appointment lies in month of Saturday
					else if (Integer.compare(monthOfSaturday, currentMonth) == 0) {

						// Appointment not in period
						if (dayOfSaturday < currentDay) {
							iterator.remove();
						}
					}

				}

			} else {
				/** case Filtering Across Annual Limits: */
				/** case: month of Monday is in old year month of Saturday in new year */

				// Appointment lies in month of Monday
				if (Integer.compare(monthOfMonday, currentMonth) == 0) {

					// Appointment not in period
					if (dayOfMonday > currentDay) {
						iterator.remove();
					}
				}

				// Appointment lies in month of Saturday
				else if (Integer.compare(monthOfSaturday, currentMonth) == 0) {

					// Appointment not in period
					if (dayOfSaturday < currentDay) {
						iterator.remove();
					}
				}

			}

		}
		return receiving;

	}

	/**
	 * computes the date of Monday in current week
	 * 
	 * @return the computed date
	 * @throws ParseException
	 */
	public static DayAndMonth getDateOfMonday() {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		/** computing date of Monday in current week */
		calendar.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - Calendar.MONDAY));

		return new DayAndMonth(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));
	}

	/**
	 * computes the date of Saturday in current week
	 * 
	 * @return the computed date
	 */
	public static DayAndMonth getDateOfSaturday() {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		/** computing date of Friday in current week */
		calendar.add(Calendar.DAY_OF_WEEK, Calendar.SATURDAY - dayOfWeek);
		return new DayAndMonth(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));
	}

	/**
	 * yields a list of today's appointments extracted from the receiving
	 * 
	 * @param lstAppointments
	 *            - the receiving
	 * @return the today's appointments
	 */
	public static List<Appointment> getTodaysAppointments(List<Appointment> lstAppointments, int dayOfMonth) {

		List<Appointment> response = new ArrayList<Appointment>(lstAppointments);

		Iterator<Appointment> iterator = response.iterator();

		while (iterator.hasNext()) {
			Appointment current = iterator.next();
			time.Date date = current.getDate();

			if (!(Integer.compare(date.getDayOfMonth(), dayOfMonth) == 0)) {

				iterator.remove();
			}

		}

		return response;
	}

	/**
	 * removes all appointments not having the desired service
	 * 
	 * @param lstAppointments
	 *            - the appointments
	 * @param service
	 *            - the desired service
	 * @return - all appointments having the desired service
	 */
	public static List<Appointment> removeAppointmentsNotHavingService(List<Appointment> lstAppointments,
			String service) {
		List<Appointment> response = new ArrayList<Appointment>(lstAppointments);

		Iterator<Appointment> iterator = response.iterator();

		while (iterator.hasNext()) {
			Appointment current = iterator.next();

			String currentService = current.getService();

			if ((currentService == null) || (!currentService.equalsIgnoreCase(Constants.RADABHOLUNG))) {

				iterator.remove();
			}
		}

		return response;
	}

	/**
	 * determines the current appointment
	 * 
	 * @param lstAppointments
	 *            - list of appointments
	 * @return null if there is no appointment, otherwise the current appointment
	 */
	public static Appointment getCurrentAppointment(List<Appointment> lstAppointments) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.GERMAN);
		Calendar now = new GregorianCalendar();

		Calendar timeOfAppointment = new GregorianCalendar();

		StringBuilder builder = new StringBuilder();
		for (Appointment current : lstAppointments) {

			String service = current.getService();
			time.Time startTime = current.getStartTime();
			time.Date date = current.getDate();

			/** the hour */
			int currentHour = startTime.getHour();
			String currentHourAsString = String.valueOf(currentHour);

			if (currentHourAsString.length() == 1) {
				currentHourAsString = "0" + currentHourAsString;
			}

			/** the minute */
			int currentMinute = startTime.getMinute();
			String currentMinuteAsString = String.valueOf(currentMinute);

			if (currentMinuteAsString.length() == 1) {
				currentMinuteAsString = "0" + currentMinuteAsString;
			}

			/** the second */
			int currentSecond = startTime.getSecond();
			String currentSecondAsString = String.valueOf(currentSecond);

			if (currentSecondAsString.length() == 1) {
				currentSecondAsString = "0" + currentSecondAsString;
			}

			/** the day */
			int day = date.getDayOfMonth();
			String dayAsString = String.valueOf(day);

			if (dayAsString.length() == 1) {
				dayAsString = "0" + dayAsString;
			}

			/** the month */
			int month = date.getMonth().getValue();
			String monthAsString = String.valueOf(month);

			if (monthAsString.length() == 1) {
				monthAsString = "0" + monthAsString;
			}

			int year = date.getYear();

			builder.append(String.valueOf(year));
			builder.append(monthAsString);
			builder.append(dayAsString);

			builder.append(currentHourAsString);
			builder.append(currentMinuteAsString);
			builder.append(currentSecondAsString);

			Date currentDate = null;
			try {
				currentDate = simpleDateFormat.parse(builder.toString());
				// reset builder:
				builder.setLength(0);
			} catch (ParseException e) {
			}

			if (currentDate != null) {
				timeOfAppointment.setTime(currentDate);
				long remainingMilliSeconds = timeOfAppointment.getTimeInMillis() - now.getTimeInMillis();
				if (remainingMilliSeconds < 0) {
					/** appointment is in future */
					long remainingSeconds = remainingMilliSeconds / 1000;
					long remainingMinutes = remainingSeconds / 60;
					if ((remainingMinutes > -30) && (remainingMinutes < 0)) {

						if ((service != null) && (service.equalsIgnoreCase(Constants.RADABHOLUNG))) {

							return current;

						}
					}

				}
			}
		}

		return null;
	}

	/***
	 * handles the format of the receiving
	 * 
	 * @param receiving
	 *            - the received component of the time.Time
	 * @return the formatted component of the time.Time
	 */
	public static String handleTime(int receiving) {

		String response = String.valueOf(receiving);

		return response.length() == 1 ? "0" + response : response;
	}

	/**
	 * yields the rowheight depending on count of appointments
	 * 
	 * @param lstAppointments
	 *            - the appointmenst
	 * @return - the rowheight n percent
	 */
	public static String determineRowHeightInPercent(List<Appointment> lstAppointments) {

		int appointmentsOnLeftSide = 0;
		int appointmentsOnRightSide = 0;

		for (Appointment appointment : lstAppointments) {

			if ((appointment.getStartTime().getHour() >= 9) && appointment.getStartTime().getHour() <= 13) {
				appointmentsOnLeftSide++;
			}

			else if ((appointment.getStartTime().getHour() >= 14) && appointment.getStartTime().getHour() <= 18) {
				appointmentsOnRightSide++;
			}

		}
		int countOfAppointments = appointmentsOnLeftSide > appointmentsOnRightSide ? appointmentsOnLeftSide
				: appointmentsOnRightSide;

		if (countOfAppointments >= 5) {
			return Constants.ROW_HEIGHT_GT5;
		}
		return Constants.ROW_HEIGHT_LT5;
	}

	/**
	 * loads the video files from the video directory
	 * 
	 * @param directory
	 *            - the directory
	 * @return - the files
	 */
	public static List<File> loadVideos(String directory) {

		File file = new File(directory);
		if (!file.exists()) {
			file.mkdir();
		}
		return Arrays.asList(new File(directory).listFiles());
	}

}
