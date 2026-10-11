package dom2app.reporting;

import java.io.IOException;

/**
 * DisasterReport interface
 */
public interface IDisasterReport {
	
	/**
	 * Main method to generate entire report
	 */
	void generateReport();
	
	/**
	 * Writes report to file
	 * @return number of lines written
	 * @throws IOException on failure to write the file
	 */
	int writeReport() throws IOException;
}
