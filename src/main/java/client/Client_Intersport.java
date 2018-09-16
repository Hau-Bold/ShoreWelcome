package client;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import html.ConcreteCommand;
import html.HtmlExecutor;
import html.HtmlExecutorAppointmentImpl;
import html.HtmlExecutorOverviewImpl;
import html.VideoWriter;
import time.DayAndMonth;
import utils.Appointment;
import utils.Constants;
import utils.DisplayType;
import utils.FilesOfFolder;
import utils.Utils;

public class Client_Intersport {

	private static List<Appointment> lstAppointments = new ArrayList<Appointment>(), lstTodaysAppointments;
	private static Appointment currentAppointment = null;
	private static HtmlExecutor executor;
	private static List<File> lstVideoFile = null;

	private static boolean isWindows, currentAppointmentIsDisplayed = Boolean.FALSE;
	private static String directoryOfICSFiles, pathOfHtmlPage, directoryOfVideoFiles, pathVideoHtml, absolutPathOfVideo,
			currentPath;
	private static int dayOfMonth, currentHour, currentMinute, index, videoCounter = 0;
	private static WebDriver driver;
	private static DisplayType displayType;
	private static Calendar calendar;
	private static Date now;
	private static DayAndMonth monday;
	private static ChromeOptions chromeOptions;
	private static DayAndMonth saturday;
	private static FilesOfFolder filesOfFolder;
	private static List<File> lstICSFiles;
	private static Boolean folderChanged, hasEnded;
	private static JavascriptExecutor javascriptExecutor;
	private static WebElement vid;

	public static void main(final String[] args) throws IOException, InterruptedException {
		isWindows = SystemUtils.IS_OS_WINDOWS;
		loadSettings(isWindows);
		lstVideoFile = Utils.loadVideos(directoryOfVideoFiles);
		chromeOptions = null;
		if (!isWindows) {
			chromeOptions = new ChromeOptions();
			/** < new > */
			chromeOptions.addArguments("--disable-application-cache");
			/** </ new > */
			chromeOptions.addArguments("--disable-infobars");
			chromeOptions.addArguments("--kiosk");

			// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			//
			// capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
			// true);
			// capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
			//
			// ChromeDriver driver = new ChromeDriver(capabilities);

			/** options get capabilities */
			// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			// ChromeOptions options = new ChromeOptions();
			// options.addArguments("test-type");
			// capabilities.setCapability("chrome.binary", "<Path to binary>");
			// capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			// webDriver = new ChromeDriver(capabilities);

			// To disable chrome caching:
			// from selenium import webdriver
			// chrome_options = webdriver.ChromeOptions()
			// chrome_options.add_argument('--disable-application-cache')
			// driver = webdriver.Chrome(chrome_options=chrome_options)

		}

		/** old: */
		// if (!isWindows) {
		// chromeOptions = new ChromeOptions();
		// chromeOptions.addArguments("--disable-infobars");
		// chromeOptions.addArguments("--kiosk");
		// }

		driver = isWindows ? new FirefoxDriver() : new ChromeDriver(chromeOptions);

		if (isWindows) {
			driver.manage().window().maximize();
			driver.manage().window().fullscreen();
		}

		driver.get("file:///" + pathOfHtmlPage);

		VideoWriter videoWriter = new VideoWriter();
		ConcreteCommand concreteCommand = new ConcreteCommand();

		while (true) {

			calendar = Calendar.getInstance();
			dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
			now = calendar.getTime();

			monday = Utils.getDateOfMonday();
			saturday = Utils.getDateOfSaturday();
			currentHour = calendar.get(Calendar.HOUR_OF_DAY);
			currentMinute = calendar.get(Calendar.MINUTE);

			/** load files */
			filesOfFolder = Utils.getICSFilesFromDirectory(directoryOfICSFiles, currentHour, currentMinute);

			lstICSFiles = filesOfFolder.getLstICSFiles();

			/** has timestamp of folder changed? */
			folderChanged = filesOfFolder.getFolderChanged();

			for (File file : lstICSFiles) {
				try {
					lstAppointments.add(Utils.generateAppointmentFromFile(file));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			/** selecting Appointments belonging to the current Week */
			lstAppointments = Utils.removeAppointmentsNotBelongingToCurrentWeek(lstAppointments, monday, saturday);

			/** appointments of today */
			lstTodaysAppointments = Utils.getTodaysAppointments(lstAppointments, dayOfMonth);

			/** appointment in next 30 minutes */
			currentAppointment = Utils.getCurrentAppointment(lstTodaysAppointments);

			if (currentAppointment != null) {
				/** there is an appointment starting within next 30 minutes */
				displayType = DisplayType.DisplayCurrentAppointment;
			}

			else {
				/** there is no current appointment */
				displayType = DisplayType.DisplayAppointments;
			}

			switch (displayType) {

			case DisplayAppointments:

				absolutPathOfVideo = lstVideoFile.get(videoCounter).getAbsolutePath();
				index = absolutPathOfVideo.lastIndexOf(File.separator);

				currentPath = absolutPathOfVideo.substring(index + 1, absolutPathOfVideo.length());

				videoWriter.write((isWindows ? "" : "assets/") + "videos" + File.separator + currentPath,
						pathVideoHtml);

				driver.get("file:///" + pathVideoHtml);

				javascriptExecutor = (JavascriptExecutor) driver;
				hasEnded = Boolean.FALSE;

				try {
					vid = driver.findElement(By.id("videoId"));
				} catch (Exception ex) {
					System.err.println(String.format("Error at time %s", now));
				}

				try {
					driver.findElement(By.tagName("body")).sendKeys(Keys.ENTER);
				} catch (Exception ex) {
					System.err.println(String.format("Error at time %s", now));
				}

				while (!hasEnded) {
					Object value = javascriptExecutor.executeScript("return arguments[0].ended", vid);
					if (value != null) {
						hasEnded = (Boolean) value;

					}
					Thread.sleep(1000);
				}

				driver.get("file:///" + pathOfHtmlPage);

				checkVideoCounter();

				if (folderChanged || currentAppointmentIsDisplayed) {

					if (currentAppointmentIsDisplayed) {
						currentAppointmentIsDisplayed = Boolean.FALSE;
						folderChanged = Boolean.TRUE;
					}

					/** this has to be done only if */
					/** i)Application is started */
					/**
					 * ii)A new Appointment was created - we do not have a control wether or not
					 * this happens on the same day as the appointments appears
					 */
					/** iii)When the date changed -> see getIcsFilesFromDirectory. */
					/***/
					/** iv) mode changed from 2 nd switch statement to first */

					executor = new HtmlExecutorOverviewImpl(lstTodaysAppointments, pathOfHtmlPage,
							Constants.TITLE_OF_PAGE_OVERVIEW, Constants.HEADER_OF_PAGE_OVERVIEW, now, isWindows);
				}
				break;

			case DisplayCurrentAppointment:

				if (!currentAppointmentIsDisplayed) {

					executor = new HtmlExecutorAppointmentImpl(currentAppointment, pathOfHtmlPage,
							Constants.TITLE_OF_PAGE_APPOINTMENT, Constants.HEADER_OF_PAGE_APPOINTMENT, isWindows);
					folderChanged = Boolean.TRUE;
					currentAppointmentIsDisplayed = Boolean.TRUE;
				}

				break;

			default:

				// do nothing
			}

			/** write Html code */

			if (executor != null) {

				if (folderChanged) {
					concreteCommand.setExecutor(executor);
					concreteCommand.execute();
					/** writing of Html code is finished */
					/** browser has to be refreshed */
					driver.navigate().refresh();

				}
				executor = null;
			}

			reset();

			try {

				Thread.sleep(1000 * 60 * Constants.DELAY_IN_MINUTES);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * resets the global variables
	 */
	private static void reset() {
		currentAppointment = null;

		if (lstTodaysAppointments != null) {
			lstTodaysAppointments.clear();
		}

		if (lstAppointments != null) {
			lstAppointments.clear();
		}

	}

	private static void checkVideoCounter() {
		videoCounter++;
		if (Integer.compare(videoCounter, lstVideoFile.size()) == 0) {

			/** resetting counter of videos */
			videoCounter = 0;
			/** video counter controlled */
		}

	}

	/**
	 * establishes settings depending on the OS
	 */
	private static void loadSettings(Boolean isWindows) {

		String driver = isWindows ? Constants.DRIVER_WINDOWS : Constants.DRIVER_LINUX;

		System.setProperty(driver,
				isWindows ? Constants.PATH_TO_EXECUTABLE_GECKO_WINDOWS : Constants.PATH_TO_EXECUTABLE_CHROM_LINUX);

		directoryOfICSFiles = isWindows ? Constants.DIRECTORY_ICS_FILES_WINDOWS : Constants.DIRECTORY_ICS_FILES_LINUX;
		pathOfHtmlPage = isWindows ? Constants.PATH_TO_SOURCE_OF_PAGE_WINDOWS : Constants.PATH_TO_SOURCE_OF_PAGE_LINUX;
		pathVideoHtml = isWindows ? Constants.Path_TO_VIDEO_HTML_WINDOWS : Constants.PATH_TO_VIDEO_HTML_LINUX;

		directoryOfVideoFiles = isWindows ? Constants.PATH_TO_VIDEO_FOLDER_WINDOWS
				: Constants.PATH_TO_VIDEO_FOLDER_LINUX;

	}

}
