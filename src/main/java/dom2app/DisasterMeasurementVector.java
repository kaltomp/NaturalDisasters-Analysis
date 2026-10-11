package dom2app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.commons.math3.util.Pair;

public class DisasterMeasurementVector implements IMeasurementVector {

	private String countryName;
	private String indicatorString;
	private List<Pair<Integer, Integer>> measurements;

	public DisasterMeasurementVector() {
		this.measurements = new ArrayList<Pair<Integer, Integer>>();
	}
	
	public DisasterMeasurementVector(String name, String indicator) {
		this.countryName = name;
		this.indicatorString = indicator;
		this.measurements = new ArrayList<Pair<Integer, Integer>>();
	}

	public DisasterMeasurementVector(String name, String indicator, List<Pair<Integer, Integer>> measurements) {
		this.countryName = name;
		this.indicatorString = indicator;
		this.measurements = measurements;
	}

	@Override
	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String name) {
		this.countryName = name;
	}

	@Override
	public String getIndicatorString() {
		return this.indicatorString;
	}

	public void setIndicatorString(String indicator) {
		this.indicatorString = indicator;
	}

	@Override
	public List<Pair<Integer, Integer>> getMeasurements() {
		return this.measurements;
	}
	
	public void addMeasurement(Pair<Integer, Integer> pair) {
		this.measurements.add(pair);
	}

	public void setMeasurements(List<Pair<Integer, Integer>> measurements) {
		this.measurements = measurements;
	}

	@Override
	public String getDescriptiveStatsAsString() {
		String descriptiveString;
		DescriptiveStatistics stats = new DescriptiveStatistics();
		int count = 0;
		
		for (Pair<Integer, Integer> m: measurements) {
			stats.addValue(m.getValue());
			if (m.getValue() > 0) {
				count++;
			}
		}
		
		descriptiveString = String.format("Max: %d, Min: %d, Years with incident: %d, Mean: %.2f, Median: %.2f, Total: %d", (int)stats.getMax(), (int)stats.getMin(), count, stats.getMean(), stats.getPercentile(50), (int)stats.getSum());
		
		return descriptiveString;
	}

	@Override
	public String getRegressionResultAsString() {
		SimpleRegression regression = new SimpleRegression();
		
		for (int i = 0; i < measurements.size(); i++) {
			regression.addData(measurements.get(i).getValue(), i);
		}
		
		double slope = regression.getSlope();
		
		if (slope > 0.1) {
			return "Tendency to increase";
		} else if (slope < -0.1) {
			return "Tendency to decrease";
		} else {
			return "Unclear tendency";
		}
	}

}
