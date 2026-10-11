package engine;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import dom2app.IMeasurementVector;
import dom2app.ISingleMeasureRequest;

public class MainControllerTest {

    private MainController controller;
    private File tempCsvFile;

    @Before
    public void setUp() throws Exception {
        controller = new MainController();
        
        // Δημιουργία ενός προσωρινού CSV αρχείου για δοκιμή
        tempCsvFile = File.createTempFile("test_data", ".csv");
        tempCsvFile.deleteOnExit();
        try (FileWriter writer = new FileWriter(tempCsvFile)) {
            writer.write("ObjectId,Country,ISO2,ISO3,Indicator,1980,1981,1982\n");
            writer.write("1,Greece,GR,GRC,Earthquakes,10,,30\n");
            writer.write("2,Italy,IT,ITA,Floods,,20,\n");
        }
    }

    @Test
    public void testLoad() throws IOException {
        List<IMeasurementVector> vectors = controller.load(tempCsvFile.getAbsolutePath(), ",");
        assertEquals(2, vectors.size());
        
        assertEquals("Greece", vectors.get(0).getCountryName());
        assertEquals("Earthquakes", vectors.get(0).getIndicatorString());
        assertEquals(3, vectors.get(0).getMeasurements().size());
        
        assertEquals((Integer)10, vectors.get(0).getMeasurements().get(0).getValue());
        assertEquals((Integer)0, vectors.get(0).getMeasurements().get(1).getValue());
        assertEquals((Integer)30, vectors.get(0).getMeasurements().get(2).getValue());
    }
    
    @Test
    public void testFindSingleCountryIndicator() throws IOException {
        controller.load(tempCsvFile.getAbsolutePath(), ",");
        
        ISingleMeasureRequest request = controller.findSingleCountryIndicator("req1", "Greece", "Earthquakes");
        assertNotNull(request);
        assertTrue(request.isAnsweredFlag());
        assertNotNull(request.getAnswer());
        assertEquals("Greece", request.getAnswer().getCountryName());
        
        ISingleMeasureRequest requestNotFound = controller.findSingleCountryIndicator("req2", "Greece", "Tornadoes");
        assertFalse(requestNotFound.isAnsweredFlag());
    }

    @Test
    public void testFindSingleCountryIndicatorYearRange() throws IOException {
        controller.load(tempCsvFile.getAbsolutePath(), ",");
        
        ISingleMeasureRequest request = controller.findSingleCountryIndicatorYearRange("req3", "Greece", "Earthquakes", 1980, 1981);
        assertNotNull(request);
        assertTrue(request.isAnsweredFlag());
        assertEquals(2, request.getAnswer().getMeasurements().size());
    }
}
