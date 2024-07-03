-- Drop tables if they exist
DROP TABLE IF EXISTS `house`;
DROP TABLE IF EXISTS `room`;
DROP TABLE IF EXISTS `device`;
DROP TABLE IF EXISTS `actuator`;
DROP TABLE IF EXISTS `sensor`;
DROP TABLE IF EXISTS `instant_time_value`;
DROP TABLE IF EXISTS `instant_time_location_value`;
DROP TABLE IF EXISTS `period_time_value`;

-- Create house table
CREATE TABLE `house` (
                         `houseID` VARCHAR(255) PRIMARY KEY,
                         `latitude` DOUBLE,
                         `longitude` DOUBLE,
                         `street` VARCHAR(255),
                         `door_number` VARCHAR(10),
                         `zip_code` VARCHAR(10),
                         `city` VARCHAR(100),
                         `country` VARCHAR(100)
);

-- Create room table
CREATE TABLE `room` (
                        `roomID` VARCHAR(255) PRIMARY KEY,
                        `houseID` VARCHAR(255),
                        `room_floor` INT,
                        `height` DOUBLE,
                        `width` DOUBLE,
                        `length` DOUBLE,
                        FOREIGN KEY (`houseID`) REFERENCES `house`(`houseID`)
);

-- Create device table
CREATE TABLE `device` (
                          `deviceID` VARCHAR(255) PRIMARY KEY,
                          `device_model` VARCHAR(255),
                          `device_status` VARCHAR(255),
                          `roomID` VARCHAR(255),
                          FOREIGN KEY (`roomID`) REFERENCES `room`(`roomID`)
);

-- Create actuator table
CREATE TABLE `actuator` (
                            `actuatorID` VARCHAR(255) PRIMARY KEY,
                            `actuator_functionalityid` VARCHAR(255) NOT NULL,
                            `upper_limit_int` INT,
                            `lower_limit_int` INT,
                            `upper_limit_decimal` DOUBLE,
                            `lower_limit_decimal` DOUBLE,
                            `precision_value` INT,
                            `deviceID` VARCHAR(255) NOT NULL,
                            FOREIGN KEY (`deviceID`) REFERENCES `device`(`deviceID`)
);

-- Create sensor table
CREATE TABLE `sensor`  (
                           `sensorID` VARCHAR(255) PRIMARY KEY,
                           `deviceID` VARCHAR(255) NOT NULL,
                           `sensor_functionalityid` VARCHAR(255) NOT NULL,
                           FOREIGN KEY (`deviceID`) REFERENCES `device`(`deviceID`)
);

CREATE TABLE `instant_time_value` (
                                      `valueID` VARCHAR(255) PRIMARY KEY,
                                      `measurement` VARCHAR(255) NOT NULL,
                                      `unit` VARCHAR(50) NOT NULL,
                                      `instant_time` TIMESTAMP NOT NULL,
                                      `sensorID` VARCHAR(255) NOT NULL,
                                      FOREIGN KEY (`sensorID`) REFERENCES `sensor`(`sensorID`)
);

CREATE TABLE `instant_time_location_value` (
                                               `valueID` VARCHAR(255) PRIMARY KEY,
                                               `measurement` VARCHAR(255) NOT NULL,
                                               `unit` VARCHAR(50) NOT NULL,
                                               `instant_time` TIMESTAMP NOT NULL,
                                               `latitude` DOUBLE NOT NULL,
                                               `longitude` DOUBLE NOT NULL,
                                               `sensorID` VARCHAR(255) NOT NULL,
                                               FOREIGN KEY (`sensorID`) REFERENCES `sensor`(`sensorID`)
);

CREATE TABLE `period_time_value` (
                                     `valueID` VARCHAR(255) PRIMARY KEY,
                                     `measurement` VARCHAR(255) NOT NULL,
                                     `unit` VARCHAR(50) NOT NULL,
                                     `start_time` TIMESTAMP NOT NULL,
                                     `end_time` TIMESTAMP NOT NULL,
                                     `sensorID` VARCHAR(255) NOT NULL,
                                     FOREIGN KEY (`sensorID`) REFERENCES `sensor`(`sensorID`)
);

-- Insert initial data into house table
INSERT INTO `house` (`houseID`, `latitude`, `longitude`, `street`, `door_number`, `zip_code`, `city`, `country`) VALUES
    ('House001', 50.7958, -4.2596, 'Rua do Amial', '123', '4435-123', 'Porto', 'Portugal');

-- Insert initial data into room table
INSERT INTO `room` (`roomID`, `houseID`, `room_floor`, `height`, `width`, `length`) VALUES
                                                                                        ('Garden', 'House001', 0, 0.0, 3.0, 4.0),
                                                                                        ('Bedroom01', 'House001', 2, 2.7, 3.5, 4.5),
                                                                                        ('Kitchen', 'House001', 0, 2.4, 2.8, 3.8),
                                                                                        ('LivingRoom', 'House001', 1, 2.5, 4.0, 5.0),
                                                                                        ('Bedroom02', 'House001', -1, 2.2, 3.2, 4.2);

-- Insert initial data into device table
INSERT INTO `device` (`deviceID`, `device_model`, `device_status`, `roomID`) VALUES
                                                                                 ('Thermometer01', 'T8115', 'ACTIVE', 'Garden'),
                                                                                 ('Anemometer', 'SL8115', 'ACTIVE', 'Garden'),
                                                                                 ('BlindRoller01', 'B8115', 'ACTIVE', 'Bedroom01'),
                                                                                 ('SolarWatchHygrometer', 'B8115', 'ACTIVE', 'Garden'),
                                                                                 ('Toaster', 'A8115', 'ACTIVE', 'Kitchen'),
                                                                                 ('ElectricExtension', 'S8115', 'ACTIVE', 'Kitchen'),
                                                                                 ('Thermometer02', 'T8115', 'ACTIVE', 'LivingRoom'),
                                                                                 ('BlindRoller02', 'SL8115', 'ACTIVE', 'LivingRoom'),
                                                                                 ('BlindRoller03', 'T8115', 'ACTIVE', 'Bedroom02'),
                                                                                 ('Thermostat', 'T8115', 'ACTIVE', 'Bedroom02'),
                                                                                 ('Grid Power Meter', 'G8115', 'ACTIVE', 'Bedroom01'),
                                                                                 ('Power Source 1', 'P8115', 'ACTIVE', 'Bedroom02'),
                                                                                 ('Power Source 2', 'P8115', 'ACTIVE', 'LivingRoom');

-- Insert initial data into actuator table
INSERT INTO `actuator` (`actuatorID`, `actuator_functionalityid`, `upper_limit_int`, `lower_limit_int`, `upper_limit_decimal`, `lower_limit_decimal`, `precision_value`, `deviceID`) VALUES
                                                                                                                                                                                         ('Actuator001', 'DecimalSetter', NULL, NULL, 30.0, 10.0, 1, 'Thermometer01'),
                                                                                                                                                                                         ('Actuator002', 'Switch', NULL, NULL, NULL, NULL, NULL, 'Anemometer'),
                                                                                                                                                                                         ('Actuator003', 'BlindSetter', NULL, NULL, NULL, NULL, NULL, 'BlindRoller01'),
                                                                                                                                                                                         ('Actuator004', 'BlindSetter', NULL, NULL, NULL, NULL, NULL, 'BlindRoller02'),
                                                                                                                                                                                         ('Actuator005', 'DecimalSetter', NULL, NULL, 25.0, 16.0, 1, 'Toaster'),
                                                                                                                                                                                         ('Actuator006', 'Switch', NULL, NULL, NULL, NULL, NULL, 'ElectricExtension'),
                                                                                                                                                                                         ('Actuator007', 'DecimalSetter', NULL, NULL, 30.0, 10.0, 1, 'Thermometer02'),
                                                                                                                                                                                         ('Actuator008', 'Switch', NULL, NULL, NULL, NULL, NULL, 'Thermostat'),
                                                                                                                                                                                         ('Actuator009', 'BlindSetter', NULL, NULL, NULL, NULL, NULL, 'BlindRoller03'),
                                                                                                                                                                                         ('Actuator010', 'DecimalSetter', NULL, NULL, 25.0, 16.0, 1, 'Thermostat');
-- Insert initial data into sensor table
INSERT INTO `sensor` (`sensorID`, `sensor_functionalityid`, `deviceID`) VALUES
                                                                            ('Sensor001', 'TemperatureCelsius', 'Thermometer01'),
                                                                            ('Sensor002', 'WindSpeedAndDirection', 'Anemometer'),
                                                                            ('Sensor003', 'Sunrise', 'SolarWatchHygrometer'),
                                                                            ('Sensor004', 'Sunset', 'SolarWatchHygrometer'),
                                                                            ('Sensor005', 'ElectricEnergyConsumption', 'Toaster'),
                                                                            ('Sensor006', 'PowerAverage', 'ElectricExtension'),
                                                                            ('Sensor007', 'TemperatureCelsius', 'Thermometer02'),
                                                                            ('Sensor008', 'BinaryStatus', 'Thermostat'),
                                                                            ('Sensor009', 'BinaryStatus', 'Thermostat'),
                                                                            ('Sensor010', 'ElectricEnergyConsumption', 'Thermostat'),
                                                                            ('Sensor011', 'Scale', 'BlindRoller03'),
                                                                            ('Sensor012', 'PowerAverage', 'Grid Power Meter'),
                                                                            ('Sensor013', 'SpecificTimePowerConsumption', 'Power Source 1'),
                                                                            ('Sensor014', 'SpecificTimePowerConsumption', 'Power Source 2'),
                                                                            ('Sensor015', 'HumidityPercentage', 'SolarWatchHygrometer'),
                                                                            ('Sensor016', 'Scale', 'BlindRoller01'),
                                                                            ('Sensor017', 'Scale', 'BlindRoller02');

-- Insert initial data into InstantTimeValue table
INSERT INTO `instant_time_value` (`valueID`, `measurement`, `unit`, `instant_time`, `sensorID`) VALUES
                                                                                                   ('Consumption13Value1', '10', 'W', '2024-04-01 12:00:00', 'Sensor013'),
                                                                                                   ('Consumption13Value2', '20', 'W', '2024-04-01 12:21:00', 'Sensor013'),
                                                                                                   ('Consumption14Value1', '30', 'W', '2024-04-01 12:35:00', 'Sensor014'),
                                                                                                   ('InsideTemperatureValue1', '20', 'ºC', '2024-04-15 08:00:00', 'Sensor001'),
                                                                                                   ('InsideTemperatureValue2', '20', 'ºC', '2024-04-15 10:00:00', 'Sensor001'),
                                                                                                   ('InsideTemperatureValue3', '20', 'ºC', '2024-04-15 12:00:00', 'Sensor001'),
                                                                                                   ('InsideTemperatureValue4', '20', 'ºC', '2024-04-15 14:00:00', 'Sensor001'),
                                                                                                   ('InsideTemperatureValue5', '20', 'ºC', '2024-04-15 16:00:00', 'Sensor001'),
                                                                                                   ('OutsideTemperatureValue1', '14', 'ºC', '2024-04-15 08:00:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue2', '17', 'ºC', '2024-04-15 09:30:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue3', '18', 'ºC', '2024-04-15 10:50:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue4', '22', 'ºC', '2024-04-15 12:35:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue5', '24', 'ºC', '2024-04-15 14:05:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue6', '24', 'ºC', '2024-04-15 15:30:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue7', '21', 'ºC', '2024-04-15 17:00:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue8', '18', 'ºC', '2024-04-15 18:45:00', 'Sensor007'),
                                                                                                   ('OutsideTemperatureValue9', '16', 'ºC', '2024-04-15 20:00:00', 'Sensor007'),
                                                                                                   ('Blinder11Value1', '50', '%', '2024-04-15 20:00:00', 'Sensor011'),
                                                                                                   ('HumidityValue1', '15', '%','2024-04-15 16:30:00', 'Sensor015'),
                                                                                                   ('HumidityValue2', '35', '%','2024-04-15 18:30:00', 'Sensor015'),
                                                                                                   ('Blinder16Value1', '20', '%', '2024-04-20 10:00:00', 'Sensor016'),
                                                                                                   ('Blinder17Value1', '100', '%', '2024-04-21 09:00:00', 'Sensor017'),
                                                                                                   ('Blinder17Value2', '70', '%', '2024-04-21 12:00:00', 'Sensor017');

-- Insert initial data into InstantTimeLocationValue table
INSERT INTO `instant_time_location_value` (`valueID`, `measurement`, `unit`, `instant_time`, `latitude`, `longitude`, `sensorID`) VALUES
                                                                                                                                     ('Sunrise1', '07:08', 'h', '2024-04-15 07:08:00', 50.7958, -4.2596, 'Sensor003'),
                                                                                                                                     ('Sunrise2', '06:55', 'h', '2024-05-15 06:55:00', 50.7958, -4.2596, 'Sensor003'),
                                                                                                                                     ('Sunrise3', '06:25', 'h', '2024-06-05 06:25:00', 50.7958, -4.2596, 'Sensor003'),
                                                                                                                                     ('Sunrise4', '05:58', 'h', '2024-07-04 05:58:00', 50.7958, -4.2596, 'Sensor003'),
                                                                                                                                     ('Sunset1', '18:45', 'h', '2024-04-15 18:45:00', 50.7958, -4.2596, 'Sensor004'),
                                                                                                                                     ('Sunset2', '19:15', 'h', '2024-05-15 19:15:00', 50.7958, -4.2596, 'Sensor004'),
                                                                                                                                     ('Sunset3', '20:15', 'h', '2024-06-05 20:15:00', 50.7958, -4.2596, 'Sensor004'),
                                                                                                                                     ('Sunset4', '21:00', 'h', '2024-07-04 21:00:00', 50.7958, -4.2596, 'Sensor004');
-- Insert initial data into PeriodTimeValue table
INSERT INTO `period_time_value` (`valueID`, `measurement`, `unit`, `start_time`, `end_time`, `sensorID`) VALUES
                                                                                                           ('GridValue1', '50', 'W', '2024-04-01 12:15:00', '2024-04-01 12:30:00', 'Sensor012'),
                                                                                                           ('GridValue2', '60', 'W', '2024-04-01 12:30:00', '2024-04-01 12:45:00', 'Sensor012');