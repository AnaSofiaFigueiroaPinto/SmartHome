package smarthome.domain.house;

/**
 * This interface of ZipCodeValidation contains a method to validate the zipCode.
 */
public interface ZipCodeValidation {

    /**
     * Method to validate a zipCode.
     * @param zipCode String that represents a zipCode.
     * @return true if the zipCode is valid.
     */
    public boolean validateZipCode(String zipCode);
}
