package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.util.Pair;

import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;
import dom2app.DisasterMeasurementDatabase;
import dom2app.DisasterMeasurementVector;
import dom2app.SingleDisasterMeasureRequest;
import dom2app.reporting.DisasterHTMLReport;
import dom2app.reporting.DisasterMarkdownReport;
import dom2app.reporting.DisasterTextReport;
import dom2app.reporting.IDisasterReport;

public class MainController implements IMainController {
	
	private DisasterMeasurementDatabase database;
	private Map<String, ISingleMeasureRequest> requests;
	
	public MainController() {
		this.database = new DisasterMeasurementDatabase();
		this.requests = new HashMap<String, ISingleMeasureRequest>();
	}

	@Override
	public List<IMeasurementVector> load(String fileName, String delimiter) throws FileNotFoundException, IOException {
		List<IMeasurementVector> measurementVectors = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;

			// skip first line
			String[] columns = br.readLine().split(delimiter);

			// ObjectId,Country,ISO2,ISO3,Indicator,1980,1981,1982,1983,1984,1985,1986,1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022

			while ((line = br.readLine()) != null) {
				String[] parts = line.split(delimiter, -1);

				if (parts.length != columns.length) {
					System.out.println("unexpected parts length:" + parts.length);
					continue;
				}

				String countryName = parts[1];
				String indicatorString = parts[4];
				List<Pair<Integer, Integer>> measurements = new ArrayList<>();

				for (int i = 5; i < parts.length; i++) {
					int year = Integer.parseInt(columns[i]);
					int value = parts[i].length() > 0 ? Integer.parseInt(parts[i]) : 0;
					measurements.add(new Pair<>(year, value));
				}

				IMeasurementVector measurementVector = new DisasterMeasurementVector(countryName, indicatorString, measurements);
				measurementVectors.add(measurementVector);
			}
		}

		catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found: " + fileName);
		} catch (IOException e) {
			throw new IOException("Error reading file: " + e.getMessage());
		}
		
		// Also set the internal database for future requests
		this.database.setMeasurements(measurementVectors);

		return measurementVectors;
	}

	@Override
	public ISingleMeasureRequest findSingleCountryIndicator(String requestName, String countryName,
			String indicatorString) throws IllegalArgumentException {
		if (requestName.isEmpty() || countryName.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		IMeasurementVector vec = this.database.getItemByCountryIndicator(countryName, indicatorString);

		SingleDisasterMeasureRequest req = new SingleDisasterMeasureRequest(requestName, countryName + "|" + indicatorString);

		if (vec != null) {
			req.setAnsweredFlag(true);
			req.setAnswer(vec);
		} else {
			req.setAnsweredFlag(false);
		}
		
		// Add request to internal database
		this.requests.put(requestName, req);
		
		return req;
	}

	@Override
	public ISingleMeasureRequest findSingleCountryIndicatorYearRange(String requestName, String countryName,
			String indicatorString, int startYear, int endYear) throws IllegalArgumentException {
		if (requestName.isEmpty() || countryName.isEmpty() || endYear < startYear) { 
			throw new IllegalArgumentException();
		}
		
		IMeasurementVector vec = this.database.getItemByCountryIndicatorYear(countryName, indicatorString, startYear, endYear);
		
		SingleDisasterMeasureRequest req = new SingleDisasterMeasureRequest(requestName, countryName + "|" + indicatorString + "|" + startYear + "|" + endYear);
		
		if (vec != null) {
			req.setAnsweredFlag(true);
			req.setAnswer(vec);
		} else {
			req.setAnsweredFlag(false);
		}
		
		// Add request to internal database
		this.requests.put(requestName, req);
		
		return req;
	}

	@Override
	public Set<String> getAllRequestNames() {
		return this.requests.keySet();
	}

	@Override
	public ISingleMeasureRequest getRequestByName(String requestName) {
		return this.requests.get(requestName);
	}

	@Override
	public ISingleMeasureRequest getRegression(String requestName) {
		// Request's measurement vector is always ready to return the correct regression
		return this.requests.get(requestName);
	}

	@Override
	public ISingleMeasureRequest getDescriptiveStats(String requestName) {
		// Request's measurement vector is always ready to return the correct descriptive stats
		return this.requests.get(requestName);
	}

	@Override
	public int reportToFile(String outputFilePath, String requestName, String reportType) throws IOException {
		DisasterMeasurementVector vec = (DisasterMeasurementVector) this.requests.get(requestName).getAnswer();
		
		if (vec == null) {
			System.out.println("Could not find request: " + requestName);
			return -1;
		}

		IDisasterReport report = null;
		
		if (reportType.toLowerCase().equals("text")) {
			report = new DisasterTextReport(outputFilePath, requestName, vec.getCountryName(),
					vec.getIndicatorString(), vec.getMeasurements(), vec.getDescriptiveStatsAsString(), vec.getRegressionResultAsString());
		} else if (reportType.toLowerCase().equals("md")) {
			report = new DisasterMarkdownReport(outputFilePath, requestName, vec.getCountryName(),
					vec.getIndicatorString(), vec.getMeasurements(), vec.getDescriptiveStatsAsString(), vec.getRegressionResultAsString());
		} else if (reportType.toLowerCase().equals("html")) {
			report = new DisasterHTMLReport(outputFilePath, requestName, vec.getCountryName(),
					vec.getIndicatorString(), vec.getMeasurements(), vec.getDescriptiveStatsAsString(), vec.getRegressionResultAsString());
		}
		
		int lines;
		if (report != null) {
			report.generateReport();
			lines = report.writeReport();
		} else {
			System.out.println("Unsupported report type: " + reportType);
			return -1;
		}
		
		return lines;
	}

}
