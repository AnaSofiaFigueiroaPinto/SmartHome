package smarthome.domain.valueobjects.zipcodevalidation;

import smarthome.domain.house.ZipCodeValidation;

public class ZipCodeValidationUSA implements ZipCodeValidation {


    @Override
    public boolean validateZipCode(String zipCode) {
        return zipCode.matches("\\d{5}(?:[-\\s]\\d{4})?");
    }
}
