package smarthome.domain.valueobjects;

import smarthome.ddd.ValueObject;

import java.util.Objects;

public class RoomDimensions implements ValueObject {
    private final double length;
    private final double width;
    private final double height;

    /**
     * Constructs a new RoomDimensions object with the specified length, width, and height.
     *
     * @param length The length of the object.
     * @param width  The width of the object.
     * @param height The height of the object.
     * @throws IllegalArgumentException if any of the dimension values are not positive.
     */
    public RoomDimensions(double length, double width, double height) {
        if (length <= 0 || width <= 0 || height < 0) {
            throw new IllegalArgumentException("Dimension values invalid");
        }
        this.length = length;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the length of the object.
     *
     * @return The length of the object.
     */
    public double getLength() {
        return length;
    }

    /**
     * Gets the width of the object.
     *
     * @return The width of the object.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the height of the object.
     *
     * @return The height of the object.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return {@code true} if this object is the same as the object
     * argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        RoomDimensions roomDimensions = (RoomDimensions) object;
        return Double.compare(roomDimensions.length, length) == 0 &&
                Double.compare(roomDimensions.width, width) == 0 &&
                Double.compare(roomDimensions.height, height) == 0;
    }

    /**
     * Returns the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(length, width, height);
    }

    /**
     * Returns a string representation of the RoomDimension object.
     *
     * @return A string containing the class name and its dimensions in the format:
     * "RoomDimensions{length=<length>, width=<width>, height=<height>}".
     */
    @Override
    public String toString() {
        return "RoomDimensions{" +
                "length=" + length +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

