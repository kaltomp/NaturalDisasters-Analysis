package dom2app;

import java.util.List;

/**
 * Domain structure that acts as database for measurement vectors. 
 * The structure is intended for use by the main controller of the engine.
 */

public interface IMeasurementDatabase {
	
	/**
	 * Add a measurement into the database
	 * @param measurement single measurement object
	 */
	void addMeasurement(IMeasurementVector measurement);
	
	/**
	 * Set the measurement database
	 * @param list populated measurement database
	 */
	void setMeasurements(List<IMeasurementVector> list);
	
	/**
	 * 
	 * @param country String that represents the country to be searched
	 * @param indicator String that represents the indicator to be searched
	 * @return returns the matching IMeasurementVector or null
	 */
	IMeasurementVector getItemByCountryIndicator(String country, String indicator);

	/**
	 * 
	 * @param country String that represents the country to be searched
	 * @param indicator String that represents the indicator to be searched
	 * @param start int that indicates the starting year
	 * @param end int that indicates the end year
	 * @return returns the matching IMeasurementVector or null
	 */
	IMeasurementVector getItemByCountryIndicatorYear(String country, String indicator, int start, int end);
	
	/**
	 * 
	 * @param country String that represents the country to be searched
	 * @return returns a list of matching IMeasurementVector objects
	 */
	List<IMeasurementVector> getItemsByCountry(String country);
	
	/**
	 * 
	 * @param indicator String that represents the indicator to be searched
	 * @return returns a list of matching IMeasurementVector objects
	 */
	List<IMeasurementVector> getItemsByIndicator(String indicator);
}
