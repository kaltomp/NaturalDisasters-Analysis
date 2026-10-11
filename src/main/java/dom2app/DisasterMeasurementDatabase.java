package dom2app;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;

public class DisasterMeasurementDatabase implements IMeasurementDatabase {
	
	private List<IMeasurementVector> database;
	
	public DisasterMeasurementDatabase() {}
	
	public DisasterMeasurementDatabase(List<IMeasurementVector> database) {
		this.database = database;
	}

	@Override
	public void addMeasurement(IMeasurementVector measurement) {
		this.database.add(measurement);
	}
	
	@Override
	public void setMeasurements(List<IMeasurementVector> list) {
		this.database = list;
	}

	@Override
	public IMeasurementVector getItemByCountryIndicator(String country, String indicator) {
		for (IMeasurementVector m: database) {
			if (m.getCountryName().equals(country) && m.getIndicatorString().equals(indicator)) {
				return m;
			}
		}
		return null;
	}

	@Override
	public IMeasurementVector getItemByCountryIndicatorYear(String country, String indicator, int start, int end) {
		for (IMeasurementVector m: database) {
			if (m.getCountryName().equals(country) && m.getIndicatorString().equals(indicator)) {
				DisasterMeasurementVector newVector = new DisasterMeasurementVector(m.getCountryName(), m.getIndicatorString());
				
				List<Pair<Integer, Integer>> mm = m.getMeasurements();
				
				// Create an adjusted measurement vector for the specified range
				// as a consequence of that, when regression or descriptive stast
				// are requested, the data will always be accurate for the specified
				// time frame
				for (int i = 0; i < mm.size(); i++) {
					int year = mm.get(i).getKey();
					if (start <= year && year <= end) {
						newVector.addMeasurement(new Pair<Integer, Integer>(year, mm.get(i).getValue()));
					}
				}
				
				return newVector;
			}
		}
		return null;
	}

	@Override
	public List<IMeasurementVector> getItemsByCountry(String country) {
		List<IMeasurementVector> list = new ArrayList<IMeasurementVector>();
		
		for (IMeasurementVector m: database) {
			if (m.getCountryName().equals(country)) {
				list.add(m);
			}
		}
		return list;
	}

	@Override
	public List<IMeasurementVector> getItemsByIndicator(String indicator) {
		List<IMeasurementVector> list = new ArrayList<IMeasurementVector>();
		
		for (IMeasurementVector m: database) {
			if (m.getIndicatorString().equals(indicator)) {
				list.add(m);
			}
		}
		return list;
	}

}
