package dom2app.reporting;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.math3.util.Pair;

public class DisasterTextReport implements IDisasterReport {
	protected String filename;
	protected String reportTitle;
	protected String countryName;
	protected String type;
	protected List<Pair<Integer, Integer>> measurements;
	protected String statistics;
	protected String regression;
	
	protected String report = "";
	
	public static final String delimeter = "\t";
	
	public DisasterTextReport() {}
	
	public DisasterTextReport (String filename, String reportTitle, String countryName, String type, List<Pair<Integer, Integer>> measurements, String statistics, String regression) {
		this.filename = filename;
		this.reportTitle = reportTitle;
		this.countryName = countryName;
		this.type = type;
		this.measurements = measurements;
		this.statistics = statistics;
		this.regression = regression;
	}

	@Override
	public void generateReport() {
		this.addRequestName();
		this.addCountryName();
		this.addDisasterType();
		this.addMeasurements();
		this.addStatistics();
		this.addRegression();
	}

	protected void addRequestName() {
		this.report += "Title:" + DisasterTextReport.delimeter + this.reportTitle + "\n";
	}

	protected void addCountryName() {
		this.report += "Country:" + DisasterTextReport.delimeter + this.countryName + "\n";
	}

	protected void addDisasterType() {
		this.report += "Disaster type: " + DisasterTextReport.delimeter + this.type + "\n";
	}

	protected void addMeasurements() {
		this.report += "Measurements:\n";
		for (Pair<Integer, Integer> m: this.measurements) {
			this.report += DisasterTextReport.delimeter + m.getKey() + ": " + m.getValue() + "\n";
		}
		this.report += "\n";
	}

	protected void addStatistics() {
		this.report += "Descriptive stats:\n";
		this.report += DisasterTextReport.delimeter + this.statistics + "\n";
	}

	protected void addRegression() {
		this.report += "Regression:\n";
		this.report += DisasterTextReport.delimeter + this.regression + "\n";
	}

	@Override
	public int writeReport() throws IOException {
		try (FileWriter textReport = new FileWriter(this.filename)) {
			textReport.write(this.report);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
		
		// Count lines
		int lines = 0;
		for (int i = 0; i < this.report.length(); i++) {
		    if (this.report.charAt(i) == '\n') {
		        lines++;
		    }
		}
		
		return lines;
	}

}
