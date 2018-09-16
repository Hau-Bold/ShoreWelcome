package utils;

import java.io.File;
import java.util.List;

/**
 * the class FilesOfFolder
 */
public class FilesOfFolder {

	private List<File> lstICSFiles;
	private Boolean folderChanged;

	/**
	 * Constructor.
	 * 
	 * @param lstICSFiles
	 *            - the list of *.ics files
	 * @param folderChanged
	 *            - to record whether or not the folder changed (are there new
	 *            appointments?)
	 */
	public FilesOfFolder(List<File> lstICSFiles, Boolean folderChanged) {

		this.lstICSFiles = lstICSFiles;
		this.folderChanged = folderChanged;

	}

	public List<File> getLstICSFiles() {
		return lstICSFiles;
	}

	public void setLstICSFiles(List<File> lstAppointments) {
		this.lstICSFiles = lstAppointments;
	}

	public Boolean getFolderChanged() {
		return folderChanged;
	}

	public void setFolderChanged(Boolean folderChanged) {
		this.folderChanged = folderChanged;
	}

}
