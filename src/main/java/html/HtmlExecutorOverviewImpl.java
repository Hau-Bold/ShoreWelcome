package html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import utils.Appointment;
import utils.Constants;
import utils.Customer;
import utils.Utils;

/**
 * the class HtmlExecutor
 *
 */
public class HtmlExecutorOverviewImpl implements HtmlExecutor {

	private String path;
	private String title;
	private String header;
	private LocalDate localDate;
	private int dayOfToday;
	private String monthOfToday;
	private int year;
	private List<Appointment> lstAppointmentsOfToday;

	private final int minFullHourOfTable = 9;
	private final int maxFullHourOfTable = 13;

	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM", Locale.GERMAN);
	private String dayOfWeekInGerman;
	private Boolean isWindows;

	/**
	 * Constructor.
	 * 
	 * @param appointmentsSeparatedByDayOfWeek
	 *            - the appointmentsSeparatedByDayOfWeek
	 * @param path
	 *            - path
	 * @param title
	 *            - title
	 * @param header
	 *            - header
	 * @param isWindows
	 * @param monday
	 *            -Monday
	 * @param saturday
	 *            - Saturday
	 */
	public HtmlExecutorOverviewImpl(List<Appointment> lstTodaysAppointments, String path, String title, String header,
			Date today, Boolean isWindows) {

		/** process today date: */
		localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.dayOfToday = localDate.getDayOfMonth();
		this.year = localDate.getYear();
		this.monthOfToday = simpleDateFormat.format(today.getTime());
		this.dayOfWeekInGerman = localDate.format(DateTimeFormatter.ofPattern("EEEE", Locale.GERMAN));

		/** setting appointments for each day: */
		if (lstTodaysAppointments != null) {
			lstAppointmentsOfToday = lstTodaysAppointments;
		}

		this.path = path;
		this.title = title;
		this.header = header;
		this.isWindows = isWindows;
	}

	/**
	 * write the head of the Html document
	 * 
	 * @param bufferedWriter
	 *            - the bufferedWriter
	 * @throws IOException
	 */
	@Override
	public void writeHeadStart(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("<!doctype html>");
		bufferedWriter.newLine();
		bufferedWriter.write("<html>");
		bufferedWriter.newLine();
		bufferedWriter.write("<head>");
		bufferedWriter.newLine();
		bufferedWriter.write("<link href=\"");

		if (!isWindows) {
			bufferedWriter.write(Constants.PATH_TO_ROBOTO_FONT_LINUX + "\"");
		} else {
			bufferedWriter.write(Constants.PATH_TO_ROBOTO_FONT_WINDOWS + "\"");
		}

		bufferedWriter.write(" rel='stylesheet' type='text/css'>");
		bufferedWriter.newLine();
		bufferedWriter.write("<meta charset=\"UTF-8\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<title>" + title + "</title>");
		bufferedWriter.newLine();
	}

	@Override
	public void writeHeadEnd(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("</head>");
		bufferedWriter.newLine();

	}

	@Override
	public void writeBodyStart(BufferedWriter bufferedWriter, String header, int dayOfToday, String monthOfToday,
			int year) throws IOException {

		bufferedWriter.write("<body id=\"body\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<img src=\"");

		if (!isWindows) {
			bufferedWriter.write(Constants.INTERSPORT_LOGO_LINUX + "\"");
		} else {
			bufferedWriter.write(Constants.INTERSPORT_LOGO_WINDOWS + "\"");
		}
		bufferedWriter.write(" class=\"Intersportlogo\" alt = \"Intersport_Logo_blank\" align=\"left\"/>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("<h1>");
		bufferedWriter.newLine();
		bufferedWriter.write(String.format(header, dayOfWeekInGerman, dayOfToday, monthOfToday, year));
		bufferedWriter.newLine();
		bufferedWriter.write("</h1>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
	}

	@Override
	public void writeBodyEnd(BufferedWriter bufferedWriter) throws IOException {

		bufferedWriter.write("</body>");
		bufferedWriter.newLine();
		bufferedWriter.write("</html>");

	}

	@Override
	public void writeStyle(BufferedWriter bufferedWriter) throws IOException {

		// from top to bottom:
		bufferedWriter.write("<style>");
		bufferedWriter.newLine();

		/** html */
		bufferedWriter.write("html");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("height: 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** body */
		bufferedWriter.write("body");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("min-height: 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** h1 */
		bufferedWriter.write("h1 {");
		bufferedWriter.newLine();
		bufferedWriter.write("color: #001F4F;");
		bufferedWriter.newLine();
		bufferedWriter.write("clear: right;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** Logo */
		bufferedWriter.write(".Intersportlogo {");
		bufferedWriter.newLine();
		bufferedWriter.write("float:left;");
		bufferedWriter.newLine();
		bufferedWriter.write("width: 15%;");
		bufferedWriter.newLine();
		bufferedWriter.write("display: block;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** body - start */
		bufferedWriter.write("#body {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-size: 100% 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		if (!isWindows) {
			bufferedWriter.write("background-image: url(\" " + Constants.BACKGROUND_IMAGE_LINUX + "\");");
		} else {
			bufferedWriter.write("background-image: url(\" " + Constants.BACKGROUND_IMAGE_WINDOWS + "\");");

		}
		bufferedWriter.newLine();
		bufferedWriter.write("font-family: Roboto, sans-serif;");
		bufferedWriter.newLine();
		bufferedWriter.write("background-repeat: no-repeat;");
		bufferedWriter.newLine();
		bufferedWriter.write("font-size: 20px;");
		bufferedWriter.newLine();
		bufferedWriter.write("margin: 0;");
		bufferedWriter.newLine();
		bufferedWriter.write("padding: 0;");
		bufferedWriter.newLine();
		bufferedWriter.write("min-height: 100%");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		/** remove */

		// advertisement
		bufferedWriter.write("#advertisementId");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("color: " + Constants.WHITE + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("clear: right;");
		bufferedWriter.newLine();
		bufferedWriter.write("font-size: 40px;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table wrapper */
		bufferedWriter.write("#tableWrapperId {");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table */
		bufferedWriter.write("#tableId {");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		bufferedWriter.write("width:80%;");
		bufferedWriter.newLine();
		bufferedWriter.write("height:80%;");
		bufferedWriter.newLine();
		bufferedWriter.write("table-spacing: 2px;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table head */
		bufferedWriter.write(".tableheader {");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table rows */
		bufferedWriter.write(".tableRow {");
		bufferedWriter.newLine();
		bufferedWriter.write("line-height: " + Utils.determineRowHeightInPercent(lstAppointmentsOfToday) + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		// even
		bufferedWriter.write("tr:nth-child(even) {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-color:" + Constants.WHITE + ";");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		// odd
		bufferedWriter.write("tr:nth-child(odd) {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-color:" + Constants.GRAY + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table data for time */
		bufferedWriter.write(".tableDataTime {");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		bufferedWriter.write("width: 5%;");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		bufferedWriter.write("border: 2px solid black;");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table data for appointments */
		bufferedWriter.write(".tableData {");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: center;");
		bufferedWriter.newLine();
		bufferedWriter.write("width: 35%;");
		bufferedWriter.newLine();
		bufferedWriter.write("border: 2px solid black;");
		bufferedWriter.newLine();
		bufferedWriter.write("filter:alpha(opacity=90);");
		bufferedWriter.newLine();
		bufferedWriter.write("-moz-opacity: 0.90;");
		bufferedWriter.newLine();
		bufferedWriter.write("opacity: 0.90;");
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** the table data for appointments that should be colored */
		bufferedWriter.write(".appointmentNotEmpty {");
		bufferedWriter.newLine();
		bufferedWriter.write("background-color: " + Constants.CORAL + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("text-align: left;");
		bufferedWriter.newLine();
		bufferedWriter.write("padding: 6px;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		bufferedWriter.write("</style>");
		bufferedWriter.newLine();

	}

	public void writeTable(BufferedWriter bufferedWriter, String dayOfWeek) throws IOException {

		bufferedWriter.write("<div id =\"tableWrapperId\" align=\"center\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<table id=\"tableId\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<thead class=\"tableheader\">");
		bufferedWriter.write("</thead>");
		bufferedWriter.newLine();
		bufferedWriter.write("<tbody>");
		bufferedWriter.newLine();

		for (int clockHour = minFullHourOfTable; clockHour <= maxFullHourOfTable; clockHour++) {

			/** appointment starts at full hour */
			bufferedWriter.write("<tr class=\"tableRow\">");
			bufferedWriter.newLine();
			bufferedWriter.write("<td class=\"tableDataTime\">" + clockHour + ":30" + "</td>");
			bufferedWriter.newLine();

			Appointment appointmentAtHalfHourLeftColumn = writeAppointmentAtHalfHour(bufferedWriter, clockHour,
					lstAppointmentsOfToday);

			if (appointmentAtHalfHourLeftColumn != null) {
				writeAppointment(appointmentAtHalfHourLeftColumn, bufferedWriter);
			} else {
				bufferedWriter.write("<td class=\"tableData\">");
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");
			bufferedWriter.newLine();

			bufferedWriter.write("<td class=\"tableDataTime\">" + (clockHour + 5) + ":00" + "</td>");
			bufferedWriter.newLine();

			Appointment appointmentAtFullHourRightColumn = writeAppointmentAtFullHour(bufferedWriter, (clockHour + 5),
					lstAppointmentsOfToday);

			if (appointmentAtFullHourRightColumn != null) {
				writeAppointment(appointmentAtFullHourRightColumn, bufferedWriter);
			} else {
				bufferedWriter.write("<td class=\"tableData\">");
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");

			bufferedWriter.write("</tr>");
			bufferedWriter.newLine();

			// ro zu ende!

			if (Integer.compare(clockHour, maxFullHourOfTable) != 0) {
				/** appointment starts a half hour later */
				bufferedWriter.write("<tr class=\"tableRow\">");
				bufferedWriter.newLine();
				int clockhourtemp = clockHour + 1;
				bufferedWriter.write("<td class=\"tableDataTime\">" + clockhourtemp + ":00" + "</td>");
				bufferedWriter.newLine();
				Appointment appointmentAtFullHourLeftColumn = writeAppointmentAtFullHour(bufferedWriter, clockHour + 1,
						lstAppointmentsOfToday);

				if (appointmentAtFullHourLeftColumn != null) {
					writeAppointment(appointmentAtFullHourLeftColumn, bufferedWriter);
				} else {
					bufferedWriter.write("<td class=\"tableData\">");
				}
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");
			bufferedWriter.newLine();

			if (Integer.compare(clockHour, maxFullHourOfTable) != 0) {
				bufferedWriter.newLine();
				bufferedWriter.write("<td class=\"tableDataTime\">" + (clockHour + 5) + ":30" + "</td>");
				bufferedWriter.newLine();
				Appointment appointmentAtHalfHourRightColumn = writeAppointmentAtHalfHour(bufferedWriter,
						(clockHour + 5), lstAppointmentsOfToday);

				if (appointmentAtHalfHourRightColumn != null) {

					writeAppointment(appointmentAtHalfHourRightColumn, bufferedWriter);
				} else {
					bufferedWriter.write("<td class=\"tableData\">");
				}
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");
			bufferedWriter.newLine();

			/** row is written */
			bufferedWriter.write("</tr>");
			bufferedWriter.newLine();

		}
		bufferedWriter.write("</tbody>");
		bufferedWriter.newLine();
		bufferedWriter.write("</table>");
		bufferedWriter.newLine();
		bufferedWriter.write("</div>");
		bufferedWriter.newLine();
	}

	public Appointment writeAppointmentAtFullHour(BufferedWriter bufferedWriter, int clockHour,
			List<Appointment> lstAppointment) throws IOException {

		for (Appointment appointment : lstAppointment) {
			int hour = appointment.getStartTime().getHour();
			int minute = appointment.getStartTime().getMinute();

			if (Integer.compare(hour, clockHour) == 0
					&& !((Integer.compare(minute, 30) == 0) || (Integer.compare(minute, 45) == 0))) {

				return appointment;

			}

		}
		return null;

	}

	public Appointment writeAppointmentAtHalfHour(BufferedWriter bufferedWriter, int clockHour,
			List<Appointment> lstAppointment) throws IOException {

		for (Appointment appointment : lstAppointment) {

			int hour = appointment.getStartTime().getHour();
			int minute = appointment.getStartTime().getMinute();

			if (Integer.compare(hour, clockHour) == 0
					&& ((Integer.compare(minute, 30)) == 0 || (Integer.compare(minute, 45) == 0))) {

				return appointment;
			}

		}
		return null;

	}

	@Override
	public void write() throws IOException {

		File file = new File(path);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}

		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		writeHeadStart(bufferedWriter);
		writeStyle(bufferedWriter);
		writeHeadEnd(bufferedWriter);
		writeBodyStart(bufferedWriter, header, dayOfToday, monthOfToday, year);
		if (lstAppointmentsOfToday.size() > 0) {

			if (dayOfWeekInGerman.equals(Constants.SATURDAY)) {
				writeTableForSaturday(bufferedWriter, dayOfWeekInGerman);
			} else {
				writeTable(bufferedWriter, dayOfWeekInGerman);
			}

		} else {
			writeAdvertisement(bufferedWriter);
		}
		writeBodyEnd(bufferedWriter);

		bufferedWriter.close();

	}

	private void writeTableForSaturday(BufferedWriter bufferedWriter, String dayOfWeekInGerman) throws IOException {
		bufferedWriter.write("<div id =\"tableWrapperId\" align=\"center\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<table id=\"tableId\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<thead class=\"tableheader\">");
		bufferedWriter.write("</thead>");
		bufferedWriter.newLine();
		bufferedWriter.write("<tbody>");
		bufferedWriter.newLine();

		// writing the rows:

		for (int clockHour = minFullHourOfTable; clockHour < maxFullHourOfTable; clockHour++) {

			/** appointment starts at full hour */
			bufferedWriter.write("<tr class=\"tableRow\">");
			bufferedWriter.newLine();
			bufferedWriter.write("<td class=\"tableDataTime\">" + clockHour + ":00" + "</td>");
			bufferedWriter.newLine();

			Appointment appointmentAtFullHourLeftColumn = writeAppointmentAtFullHour(bufferedWriter, clockHour,
					lstAppointmentsOfToday);

			if (appointmentAtFullHourLeftColumn != null) {
				writeAppointment(appointmentAtFullHourLeftColumn, bufferedWriter);
			} else {
				bufferedWriter.write("<td class=\"tableData\">");
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");
			bufferedWriter.newLine();

			bufferedWriter.write("<td class=\"tableDataTime\">" + (clockHour + 4) + ":30" + "</td>");
			bufferedWriter.newLine();

			Appointment appointmentAtHalfHourRightColumn = writeAppointmentAtHalfHour(bufferedWriter, (clockHour + 4),
					lstAppointmentsOfToday);

			if (appointmentAtHalfHourRightColumn != null) {
				writeAppointment(appointmentAtHalfHourRightColumn, bufferedWriter);
			} else {
				bufferedWriter.write("<td class=\"tableData\">");
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");

			bufferedWriter.write("</tr>");
			bufferedWriter.newLine();

			/** row is finished */

			/** appointment starts a half hour later */
			bufferedWriter.write("<tr class=\"tableRow\">");
			bufferedWriter.newLine();
			bufferedWriter.write("<td class=\"tableDataTime\">" + clockHour + ":30" + "</td>");
			bufferedWriter.newLine();
			Appointment appointmentAtHalfHourLeftColumn = writeAppointmentAtHalfHour(bufferedWriter, clockHour,
					lstAppointmentsOfToday);

			if (appointmentAtHalfHourLeftColumn != null) {
				writeAppointment(appointmentAtHalfHourLeftColumn, bufferedWriter);
			} else {
				bufferedWriter.write("<td class=\"tableData\">");
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");
			bufferedWriter.newLine();

			if (Integer.compare(clockHour, maxFullHourOfTable) != 0) {
				bufferedWriter.newLine();
				bufferedWriter.write("<td class=\"tableDataTime\">" + (clockHour + 4) + ":00" + "</td>");
				bufferedWriter.newLine();
				Appointment appointmentAtFullHourRightColumn = writeAppointmentAtFullHour(bufferedWriter,
						(clockHour + 4), lstAppointmentsOfToday);

				if (appointmentAtFullHourRightColumn != null) {

					writeAppointment(appointmentAtFullHourRightColumn, bufferedWriter);
				} else {
					bufferedWriter.write("<td class=\"tableData\">");
				}
			}
			bufferedWriter.newLine();
			bufferedWriter.write("</td>");
			bufferedWriter.newLine();

			/** row is written */
			bufferedWriter.write("</tr>");
			bufferedWriter.newLine();

		}
		bufferedWriter.write("</tbody>");
		bufferedWriter.newLine();
		bufferedWriter.write("</table>");
		bufferedWriter.newLine();
		bufferedWriter.write("</div>");
		bufferedWriter.newLine();

	}

	/***
	 * writes the advertisement between the video scenes
	 * 
	 * @param bufferedWriter
	 * 
	 * @throws IOException
	 *             - in case of technical error
	 */
	private void writeAdvertisement(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("<div id =\"advertisementId\" align=\"center\">");
		bufferedWriter.newLine();
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write(Constants.ADVERTISEMENT);
		bufferedWriter.write("</div>");

	}

	/***
	 * writes the service
	 * 
	 * @param bufferedWriter
	 *            - the bufferedWriter
	 * @param service
	 *            - the service
	 * @throws IOException
	 *             - in case of technical error
	 */
	private void writeService(BufferedWriter bufferedWriter, String service) throws IOException {
		bufferedWriter.write("<b>");
		bufferedWriter.newLine();
		bufferedWriter.write(service);
		bufferedWriter.newLine();
		bufferedWriter.write("</b>");
	}

	/***
	 * writes the customer
	 * 
	 * @param bufferedWriter
	 *            - the bufferedWriter
	 * @param customer
	 *            - the customer
	 * @throws IOException
	 *             - in case of technical error
	 */
	@Override
	public void writeCustomer(BufferedWriter bufferedWriter, Customer customer) throws IOException {
		bufferedWriter.write(Constants.CUSTOMER + Constants.COLON + " " + customer.getSalutation() + " "
				+ customer.getPreName() + " " + customer.getCustomerName());
	}

	/**
	 * writes the appointment
	 * 
	 * @param appointment
	 *            - the appointment
	 * @param bufferedWriter
	 *            - the bufferedWriter
	 * @throws IOException
	 *             - in case of technical error
	 */
	private void writeAppointment(Appointment appointment, BufferedWriter bufferedWriter) throws IOException {

		int minute = appointment.getStartTime().getMinute();
		String service = appointment.getService();
		Customer customer = appointment.getCustomer();
		String employee = appointment.getEmployee();

		bufferedWriter.write("<td class=\"tableData appointmentNotEmpty\">");
		bufferedWriter.newLine();

		if (Integer.compare(minute, 15) == 0) {
			bufferedWriter.write("</br>");
			bufferedWriter.newLine();
		}

		if (service != null) {
			writeService(bufferedWriter, service);
			bufferedWriter.newLine();
			bufferedWriter.write("</br>");
			bufferedWriter.newLine();
		}

		if (customer != null) {
			writeCustomer(bufferedWriter, customer);
		}

		bufferedWriter.newLine();
		if (employee != null) {
			bufferedWriter.write("</br>");
			bufferedWriter.write(Constants.EMPLOYEE + Constants.COLON + "  " + employee);
		}
		bufferedWriter.newLine();
	}

}
