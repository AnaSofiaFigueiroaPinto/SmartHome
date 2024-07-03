package smarthome.domain.valueobjects.zipcodevalidation;

import smarthome.domain.house.ZipCodeValidation;

public class ZipCodeValidationUK implements ZipCodeValidation {


    @Override
    public boolean validateZipCode(String zipCode) {
        return zipCode.matches("^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$");
    }

}
