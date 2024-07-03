package smarthome.util;

import smarthome.domain.valueobjects.Reading;

public class ReadingUtil {

    /**
     * Method that retrieves the attributes of a Reading measurement object and returns them as single string.
     * Works for Reading objects that have multiple measurements and units separated by a ";" or Reading objects that have a measurement but no unit.
     * Currently, assumes that the measurement at position 1 is related to the unit at position 1 and so on.
     *
     * @param reading Reading object
     * @return String with the reading value(s) and unit(s) as a single string. If multiple measurements and units present, they are separated by "and".
     */

    public static String getReadingAsSingleString (Reading reading) {
        String valueWithUnit = "";

        if (reading.getMeasurement().contains(";") || reading.getUnit().contains(";")) {

            String[] valueSplit = reading.getMeasurement().split(";");
            String[] unitSplit = reading.getUnit().split(";");

            for (int i = 0; i < valueSplit.length; i++) {
                if (unitSplit.length == 1) {
                    valueWithUnit = valueWithUnit + valueSplit[i] + " " +reading.getUnit();

                    if (i < valueSplit.length - 1) {
                        valueWithUnit = valueWithUnit + " and ";
                    }
                    continue;
                }

                if (unitSplit[i].equals("*")) {
                    valueWithUnit = valueWithUnit + valueSplit[i];
                } else {
                    valueWithUnit = valueWithUnit + valueSplit[i] + " " + unitSplit[i];
                }

                if (i < valueSplit.length - 1) {
                    valueWithUnit = valueWithUnit + " and ";
                }
            }
        } else {
            if (reading.getUnit().equals("*")) {
                valueWithUnit = valueWithUnit + reading.getMeasurement();
            } else {
                valueWithUnit = reading.getMeasurement() + " " + reading.getUnit();
            }
        }
        return valueWithUnit;
    }
}
