package html;

import java.io.BufferedWriter;
import java.io.IOException;

import utils.Customer;

/**
 * the interface HtmlExecutor
 *
 */
public interface HtmlExecutor {
	void writeHeadStart(BufferedWriter bufferedWriter) throws IOException;

	void writeStyle(BufferedWriter bufferedWriter) throws IOException;

	void writeHeadEnd(BufferedWriter bufferedWriter) throws IOException;

	void writeBodyStart(BufferedWriter bufferedWriter, String header, int dayOfToday, String monthOfToday, int year)
			throws IOException;

	void writeBodyEnd(BufferedWriter bufferedWriter) throws IOException;

	void writeCustomer(BufferedWriter bufferedWriter, Customer customer) throws IOException;

	void write() throws IOException;

}
