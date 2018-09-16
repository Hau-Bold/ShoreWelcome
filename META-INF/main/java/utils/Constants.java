package utils;

/**
 * 
 * the class constants*
 */
public class Constants {

	/** the driver */
	public static final String DRIVER_WINDOWS = "webdriver.gecko.driver";
	public static final String DRIVER_LINUX = "webdriver.chrome.driver";

	/** Windows */

	public static final String PATH_TO_EXECUTABLE_GECKO_WINDOWS = "C:\\Users\\Haubold\\workspace\\geckodriver-v0.20.0-win64\\geckodriver.exe";
	public static final String PATH_TO_SOURCE_OF_PAGE_WINDOWS = "C:\\Users\\Haubold\\Desktop\\Intersport\\appointments.html";
	public static final String DIRECTORY_ICS_FILES_WINDOWS = "C://Users//Haubold//Desktop/appointments/d7f4b0f1-d4c9-4f09-b924-937a530e0e22";
	public static final String PATH_TO_ROBOTO_FONT_WINDOWS = "C:\\Users\\Haubold\\Desktop\\Intersport\\assets\\font.css";
	public static final String INTERSPORT_LOGO_WINDOWS = "Images/logoWohllebenNeu.png";
	public static final String BACKGROUND_IMAGE_WINDOWS = "Images/background.png";
	public static final String PATH_TO_VIDEO_FOLDER_WINDOWS = "C:\\Users\\Haubold\\Desktop\\Intersport\\videos\\videos";
	public static final String Path_TO_VIDEO_HTML_WINDOWS = "C:\\Users\\Haubold\\Desktop\\Intersport\\videos\\play.html";

	/** LINUX */
	public static final String BROWSER_TITLE_LINUX = "\"*Firefox*\"";
	public static final String DIRECTORY_OF_BROWSER_LINUX = "/usr/bin/";
	public static final String PATH_TO_EXECUTABLE_CHROM_LINUX = "/opt/intersport/chromedriver/chromedriver";
	public static final String DIRECTORY_ICS_FILES_LINUX = "/opt/intersport/appointments/d7f4b0f1-d4c9-4f09-b924-937a530e0e22";
	public static final String PATH_TO_SOURCE_OF_PAGE_LINUX = "/opt/intersport/hp/appointments.html";
	public static final String PATH_TO_ROBOTO_FONT_LINUX = "asset/css/fonts.css";
	public static final String INTERSPORT_LOGO_LINUX = "assets/img/logoWohllebenNeu.png";
	public static final String BACKGROUND_IMAGE_LINUX = "assets/img/background.png";
	public static final String PATH_TO_VIDEO_FOLDER_LINUX = "/opt/intersport/hp/assets/videos";
	public static final String PATH_TO_VIDEO_HTML_LINUX = "/opt/intersport/hp/play.html";

	/**
	 * config parameters to extract data from a *.ics file
	 */
	public static final String COLON = ":";
	public static final String DTSTART = "DTSTART;";
	public static final String DTEND = "DTEND;";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String SUMMARY = "SUMMARY";
	public static final String SUBJECT = "Betreff";
	public static final String EMPLOYEE = "Mitarbeiter";
	public static final String CUSTOMER = "Kunde";
	public static final String SERVICE = "Leistung";

	/**
	 * config parameters for writing html.
	 */
	public static final String TITLE_OF_PAGE_OVERVIEW = "Tagesüberblick";
	public static final String TITLE_OF_PAGE_APPOINTMENT = "Termin";

	public static String HEADER_OF_PAGE_OVERVIEW = "%s, %s. %s %s";
	public static String HEADER_OF_PAGE_APPOINTMENT = "%s um  %s:%s Uhr";

	public static final String SALUTATION_MALE = "Herr";
	public static final Object SALUTATION_FEMALE = "Frau";

	public static final String WELCOME = "Herzlich Willkommen!";
	public static final String RADABHOLUNG = "RADABHOLUNG";
	public static final String ADVERTISEMENT = "... aus Liebe zum Sport!";

	/** Intersport color */
	public static final String WHITE = "#FFFFFF";
	public static final String GRAY = "#F0F0F0";
	public static final String GREEN = "#00CA84";
	public static final String CORAL = "#F86A6A";
	public static final String TURQUOISE = "#00B2CA";

	/** time parameters */
	public static final int DELAY_IN_MINUTES = 1;

	/** settings for the table */
	public static final String ROW_HEIGHT_GT5 = "120%";
	public static final String ROW_HEIGHT_LT5 = "160%";
	public static final String SATURDAY = "Samstag";

}
