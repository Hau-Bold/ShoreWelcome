package html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/** the class VideoWriter */
public class VideoWriter {

	/**
	 * writes the head
	 * 
	 * @param bufferedWriter
	 *            - the bufferedWriter
	 * @throws IOException
	 *             - in case of technical error
	 */
	public void writeHead(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("<!doctype html>");
		bufferedWriter.newLine();
		bufferedWriter.write("<html>");
		bufferedWriter.newLine();
		bufferedWriter.write("<head>");
		bufferedWriter.newLine();
		bufferedWriter.write("<meta charset=\"UTF-8\">");
		bufferedWriter.newLine();
	}

	/**
	 * writes the style
	 * 
	 * @param bufferedWriter
	 *            - the bufferedWriter
	 * @throws IOException
	 *             - in case of technical error
	 */
	public void writeStyle(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("<style>");
		bufferedWriter.newLine();
		/** body - start */
		bufferedWriter.write("#videoId {");
		bufferedWriter.newLine();
		bufferedWriter.write("video::-webkit-media-controls {");
		bufferedWriter.newLine();
		bufferedWriter.write(" display:none !important;");
		bufferedWriter.newLine();
		bufferedWriter.write(" }");
		bufferedWriter.newLine();
		bufferedWriter.write("</style>");
		bufferedWriter.newLine();
		bufferedWriter.write("</head>");
		bufferedWriter.newLine();

	}

	/**
	 * writes the body
	 * 
	 * @param bufferedWriter
	 *            - the bufferedWriter
	 * @param currentVideo
	 *            - the video that should be played
	 * @throws IOException
	 *             - in case of technical error
	 */
	public void writeBody(BufferedWriter bufferedWriter, String currentVideo) throws IOException {
		bufferedWriter.write("<body id=\"body\">");
		bufferedWriter.newLine();
		bufferedWriter.write("<script>");
		bufferedWriter.newLine();
		bufferedWriter.write("document.addEventListener(\"keydown\", function(e) {");
		bufferedWriter.newLine();
		bufferedWriter.write("if (e.keyCode == 13) {");
		bufferedWriter.newLine();

		// new
		bufferedWriter.write("var element = document.getElementById('videoId');");
		bufferedWriter.newLine();
		bufferedWriter.write("if (element.webkitRequestFullscreen) {");
		bufferedWriter.newLine();
		bufferedWriter.write(" element.webkitRequestFullscreen();");
		bufferedWriter.newLine();
		bufferedWriter.write("}");
		bufferedWriter.newLine();
		// new

		// bufferedWriter.write("document.getElementById('videoId').webkitRequestFullscreen()");
		// bufferedWriter.newLine();
		bufferedWriter.write("}})");
		bufferedWriter.newLine();
		bufferedWriter.write("</script>");
		bufferedWriter.newLine();
		bufferedWriter.write(" <video id=\"videoId\" autoplay controls>");
		bufferedWriter.newLine();
		bufferedWriter.write(" <source src=\"" + currentVideo + "\"" + " type=\"video/mp4\">");
		bufferedWriter.newLine();
		bufferedWriter.write("</video>");
		bufferedWriter.newLine();
		bufferedWriter.write("</body>");
		bufferedWriter.newLine();
		bufferedWriter.write("</html>");
		bufferedWriter.newLine();

	}

	public void write(String currentVideo, String path) throws IOException {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}

		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		writeHead(bufferedWriter);
		writeStyle(bufferedWriter);
		writeBody(bufferedWriter, currentVideo);
		bufferedWriter.close();

	}

}
