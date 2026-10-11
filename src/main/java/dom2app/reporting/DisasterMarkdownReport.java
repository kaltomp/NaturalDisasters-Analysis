package dom2app.reporting;

import java.util.List;

import org.apache.commons.math3.util.Pair;

public class DisasterMarkdownReport extends DisasterTextReport {

	public DisasterMarkdownReport (String filename, String reportTitle, String countryName, String type, List<Pair<Integer, Integer>> measurements, String statistics, String regression) {
		super(filename, reportTitle, countryName, type, measurements, statistics, regression);
	}
	
	@Override
	protected void addRequestName() {
		this.report += "**" + this.reportTitle + "**" + "\n\n";
	}
	
	@Override
	protected void addCountryName() {
		this.report += "Country:" + DisasterTextReport.delimeter+ "_" + this.countryName + "_" + "\n"; 
	}
	
	@Override
	protected void addMeasurements() {
		this.report += "Measurements:\n\n";
		this.report += "|Year|Disasters|\n";
		this.report += "|----|---------|\n";
		for (Pair<Integer, Integer> m: this.measurements) {
			this.report += "|" + m.getKey() + "|" + m.getValue() + "|\n";
		}
		this.report += "\n";
	}
}
