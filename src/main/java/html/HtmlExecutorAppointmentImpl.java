package html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import utils.Appointment;
import utils.Constants;
import utils.Customer;
import utils.Utils;

public class HtmlExecutorAppointmentImpl implements HtmlExecutor {

	private String path;
	private String title;
	private String header;
	private BufferedWriter bufferedWriter;
	private String service;
	private time.Time startTime;
	private Customer customer;
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
	public HtmlExecutorAppointmentImpl(Appointment appointment, String path, String title, String header,
			Boolean isWindows) {

		this.service = appointment.getService();
		this.path = path;
		this.title = title;
		this.header = header;
		this.startTime = appointment.getStartTime();
		this.customer = appointment.getCustomer();
		this.isWindows = isWindows;
	}

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
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();

		bufferedWriter.newLine();
		bufferedWriter.write("<h1>");
		bufferedWriter.newLine();

		if (service != null) {
			bufferedWriter.write(String.format(header, service, Utils.handleTime(startTime.getHour()),
					Utils.handleTime(startTime.getMinute())));
		}
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write(Constants.WELCOME);
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		bufferedWriter.write("</br>");
		bufferedWriter.newLine();
		writeCustomer(bufferedWriter, customer);
		bufferedWriter.newLine();
		bufferedWriter.write("</h1>");
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

		// html
		bufferedWriter.write("html");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("height: 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		// body
		bufferedWriter.write("body");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("background-size: 100% 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("min-height: 100%;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** h1 */
		bufferedWriter.write("h1");
		bufferedWriter.newLine();
		bufferedWriter.write("{");
		bufferedWriter.newLine();
		bufferedWriter.write("color: " + Constants.WHITE + ";");
		bufferedWriter.newLine();
		bufferedWriter.write("clear: right;");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		bufferedWriter.newLine();

		/** Logo */
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
		bufferedWriter.write("font-size: 40px;");
		bufferedWriter.newLine();
		bufferedWriter.write("margin: 0;");
		bufferedWriter.newLine();
		bufferedWriter.write("padding: 0;");
		bufferedWriter.newLine();
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

		bufferedWriter.write("</style>");
		bufferedWriter.newLine();

	}

	@Override
	public void write() throws IOException {

		File file = new File(path);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}

		bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

		writeHeadStart(bufferedWriter);
		writeStyle(bufferedWriter);
		writeHeadEnd(bufferedWriter);
		writeBodyStart(bufferedWriter, header, 0, null, 0);
		writeBodyEnd(bufferedWriter);

		bufferedWriter.close();
	}

	@Override
	public void writeCustomer(BufferedWriter bufferedWriter, Customer customer) throws IOException {
		bufferedWriter.write(customer.getSalutation() + " " + customer.getPreName() + " " + customer.getSurName());
	}

}
