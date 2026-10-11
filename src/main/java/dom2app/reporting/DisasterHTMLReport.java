package dom2app.reporting;

import java.util.List;

import org.apache.commons.math3.util.Pair;

public class DisasterHTMLReport extends DisasterTextReport {

	public DisasterHTMLReport (String filename, String reportTitle, String countryName, String type, List<Pair<Integer, Integer>> measurements, String statistics, String regression) {
		super(filename, reportTitle, countryName, type, measurements, statistics, regression);
	}
	
	@Override
	public void generateReport() {
		this.addHTMLPrefix();
		super.generateReport();
		this.addHTMLSuffix();
	}
	
	@Override
	protected void addRequestName() {
		this.report += "\t<h3>Title: <b>" + this.reportTitle + "</b></h3>\n";
	}
	
	@Override
	protected void addCountryName() {
		this.report += "\t<p>Country: <i>" + this.countryName + "</i></p>\n";
	}
	
	@Override
	protected void addDisasterType() {
		this.report += "\t<p>Disaster type: " + this.type + "</p>";
	}
	
	@Override
	protected void addMeasurements() {
		this.report += "\t<p>Measurements:</p></br>\n";
		
		this.report += "\t<table>\n";
		this.report += "\t\t<tr>\n";
		this.report += "\t\t\t<th>Year</th>\n";
		this.report += "\t\t\t<th>Disasters</th>\n";
		this.report += "\t\t</tr>\n";
		
		for (Pair<Integer, Integer> m: this.measurements) {
			this.report += "\t\t<tr>\n";
			this.report += "\t\t\t<td>" + m.getKey() + "</td>\n";
			this.report += "\t\t\t<td>" + m.getValue() + "</td>\n";
			this.report += "\t\t</tr>\n";
		}
		
		this.report += "\t</table>\n";
	}
	
	@Override
	protected void addStatistics() {
		this.report += "\t<p>Descriptive Stats: </br>" + this.statistics + "</p>\n";
	}
	
	@Override
	protected void addRegression() {
		this.report += "\t<p>Regression: </br>" + this.regression + "</p>\n";
	}
	
	private void addHTMLPrefix() {
		this.report = "<html>" + "\n";
		// Add table style
		this.report += "\t<style>" + "\n";
		this.report += "table, th, td {" + "\n";
		this.report += "\t\tborder: 1px solid black;" + "\n";
		this.report += "\t\tborder-collapse: collapse;" + "\n";
		this.report += "}" + "\n";
		this.report += "</style>" + "\n";
	}
	
	private void addHTMLSuffix() {
		this.report += "\n" + "</html>";
	}
}
