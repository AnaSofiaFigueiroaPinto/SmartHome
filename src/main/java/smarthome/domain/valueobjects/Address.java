package smarthome.domain.valueobjects;

import smarthome.domain.house.ZipCodeValidation;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Address class that represents the address of a House.
 */
public class Address {

/**
 * Declaring the attributes that will be used in constructors.
 */
    private final String street;
    private final String doorNumber;
    private final String zipCode;
    private final String city;
    private final String country;


    /**
     * Constructor that initializes the Address object with the given parameters.
     * @param street String that represents the street of the House.
     * @param doorNumber String that represents the door number of the House.
     * @param zipCode String that represents the zip code of the House.
     * @param city String that represents the city of the House.
     * @param country String that represents the country of the House.
     * @throws IllegalArgumentException if the parameters are invalid.
     */
    public Address(String street, String doorNumber, String zipCode, String city, String country)  {
        if (street == null || street.isEmpty() || street.isBlank() || doorNumber == null || doorNumber.isEmpty() || doorNumber.isBlank() || zipCode == null || zipCode.isEmpty() || zipCode.isBlank() || city == null || city.isEmpty() || city.isBlank() || country == null || country.isEmpty() || country.isBlank()) {
            throw new IllegalArgumentException("Invalid address parameters");
        }

        this.street = street;
        this.doorNumber = doorNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
        if (!validateZipCode(zipCode,country)) {
            throw new IllegalArgumentException("Invalid zip code for the country");
        }
    }

    /**
     * Method that validates the zip code of the address.
     * @param zipCode zipcode of the address.
     * @param country country of the address.
     * @return True if the zip code is valid for the country. False if it is not.
     */
    private boolean validateZipCode (String zipCode, String country)  {
        try {
            String fullNameWithClassPath = "smarthome." + "domain." + "valueobjects." + "zipcodevalidation." + "ZipCodeValidation" + country;
            ZipCodeValidation zipCodeValidation = (ZipCodeValidation) Class.forName(fullNameWithClassPath).getDeclaredConstructor().newInstance();
            return zipCodeValidation.validateZipCode(zipCode);
        }  catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException exception) {
            return false;
        }
    }

    /**
     * Method that returns the street of the address.
     * @return String representing the street of the address.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Method that returns the door number of the address.
     * @return String representing the door number of the address.
     */
    public String getDoorNumber() {
        return doorNumber;
    }

	/**
	 * Method that returns the zip code of the address.
	 * @return String representing the zip code of the address.
	 */
	public String getZipCode() {
        return zipCode;
    }

	/**
	 * Method that returns the city of the address.
	 * @return String representing the city of the address.
	 */
	public String getCity() {
        return city;
    }

	/**
	 * Method that returns the country of the address.
	 * @return String representing the country of the address.
	 */
	public String getCountry() {
        return country;
    }

	/**
	 * Method that checks if two Address are equal.
	 *
	 * @param object Object that is compared to the Address.
	 * @return True if the Address is equal to the object. False if it is not.
	 */
	@Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Address address = (Address) object;
        return Objects.equals(street, address.street) && Objects.equals(doorNumber, address.doorNumber) && Objects.equals(zipCode, address.zipCode) && Objects.equals(city, address.city) && Objects.equals(country, address.country);
    }

	/**
	 * Method that returns the hash code of the Address.
	 *
	 * @return Integer representing the hash code of the Address.
	 */
	@Override
    public int hashCode() {
        return Objects.hash(street, doorNumber, zipCode, city, country);
    }

	/**
	 * Method that returns the Address of a house.
	 * @return String representing the Address of a house.
	 */
	@Override
        public String toString() {
            return street + ", " + doorNumber + ", " + zipCode + ", " + city + ", " + country;
        }
    }