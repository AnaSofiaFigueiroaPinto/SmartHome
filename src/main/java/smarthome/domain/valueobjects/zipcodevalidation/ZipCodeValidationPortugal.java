package smarthome.domain.valueobjects.zipcodevalidation;

import smarthome.domain.house.ZipCodeValidation;

public class ZipCodeValidationPortugal implements ZipCodeValidation {


    @Override
    public boolean validateZipCode(String zipCode) {
        return zipCode.matches("\\d{4}-\\d{3}");
    }
}
