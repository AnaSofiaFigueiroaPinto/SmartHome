package smarthome.persistence.jpa.datamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import smarthome.domain.value.ImpFactoryInstantTimeLocationValue;
import smarthome.domain.value.InstantTimeLocationValue;
import smarthome.domain.valueobjects.GPSCode;
import smarthome.domain.valueobjects.Reading;
import smarthome.domain.valueobjects.SensorID;
import smarthome.domain.valueobjects.ValueID;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for InstantTimeLocationValueDataModel.
 */
class InstantTimeLocationValueDataModelTest {

    /**
     * The Value object used for testing.
     */
    private InstantTimeLocationValue value;

    /**
     * The factory used for creating the Value object.
     */
    private ImpFactoryInstantTimeLocationValue factory;

    /**
     * The ValueDataModel object used for testing.
     */
    private InstantTimeLocationValueDataModel instantTimeLocationValueDataModel;

    /**
     * Set up for each test case.
     */
    @BeforeEach
    void setUp() {
        String valueID = "valueID";
        String unit = "unit";
        String readingValue = "reading value";

        ValueID valueID1 = new ValueID(valueID);
        SensorID sensorID = new SensorID("sensorID");
        Reading reading = new Reading(readingValue, unit);

        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 3, 6, 0, 0);
        Timestamp instantTime = Timestamp.valueOf(localDateTime1);
        GPSCode gpsCode = new GPSCode(80, 99);

        factory = new ImpFactoryInstantTimeLocationValue();
        value = (InstantTimeLocationValue) factory.createValue(valueID1, sensorID, reading, instantTime, gpsCode);
    }

    /**
     * Test to verify that the InstantTimeLocationValueDataModel class can be successfully constructed with no parameters.
     */
    @Test
    void testSuccessToConstructEmptyValueDataModel() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel();
        assertNotNull(instantTimeLocationValueDataModel);
    }

    /**
     * Test to verify that the InstantTimeLocationValueDataModel class can be successfully constructed with a Value object.
     */
    @Test
    void testSuccessToConstructValueDataModel() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertNotNull(instantTimeLocationValueDataModel);
    }

    /**
     * Test to verify the successful retrieving of the valueID from the InstantTimeLocationValueDataModel class.
     */
    @Test
    void testSuccessToConstructValueDataModelWithValues() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertEquals(value.identity().toString(), instantTimeLocationValueDataModel.getValueID());
    }

    /**
     * Test to verify the successful retrieving of the reading from the InstantTimeLocationValueDataModel class.
     */
    @Test
    void testSuccessToGetReading() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertEquals(value.getReading().getMeasurement(), instantTimeLocationValueDataModel.getMeasurement());
    }

    /**
     * Test to verify the successful retrieving of the unit of the reading from the
     * InstantTimeLocationValueDataModel class.
     */
    @Test
    void testSuccessToGetUnit() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertEquals(value.getReading().getUnit(), instantTimeLocationValueDataModel.getUnit());
    }

    /**
     * Test to verify the successful retrieving of the instant time of the reading from
     * the InstantTimeLocationValueDataModel class.
     */
    @Test
    void testSuccessToGetInstantTime() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertEquals(value.getInstantTime().toString(), instantTimeLocationValueDataModel.getInstantTime().toString());
    }

    /**
     * Test to verify the successful retrieving of the latitude from the InstantTimeLocationValueDataModel class.
     */
    @Test
    void testSuccessToGetLatitude() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertEquals(value.getGpsCode().getLatitude(), instantTimeLocationValueDataModel.getLatitude());
    }

    /**
     * Test to verify the successful retrieving of the longitude from the InstantTimeLocationValueDataModel class.
     */
    @Test
    void testSuccessToGetLongitude() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertEquals(value.getGpsCode().getLongitude(), instantTimeLocationValueDataModel.getLongitude());
    }

    /**
     * Test to verify the successful retrieving of the sensorID from the InstantTimeLocationValueDataModel class.
     */
    @Test
    void testSuccessToGetSensorID() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel(value);
        assertEquals(value.getSensorID().toString(), instantTimeLocationValueDataModel.getSensorID());
    }

    /**
     * Test to verify the successful updating of the InstantTimeLocationValueDataModel class from a Value object.
     */
    @Test
    void testSuccessUpdateFromDomain() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel();
        boolean result = instantTimeLocationValueDataModel.updateFromDomain(value);
        assertTrue(result);
    }

    /**
     * Test to verify the unsuccessful update of the InstantTimeLocationValueDataModel class with a Value object.
     */
    @Test
    void testUnsuccessfulUpdateFromDomain() {
        instantTimeLocationValueDataModel = new InstantTimeLocationValueDataModel();
        boolean result = instantTimeLocationValueDataModel.updateFromDomain(null);
        assertFalse(result);
    }


}