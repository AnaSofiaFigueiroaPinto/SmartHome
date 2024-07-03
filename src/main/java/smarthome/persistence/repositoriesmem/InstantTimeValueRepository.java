package smarthome.persistence.repositoriesmem;

import smarthome.domain.repository.ValueRepository;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;

/**
 * Represents the InstantTimeValueRepository interface for persistence in memory.
 */
public interface InstantTimeValueRepository extends ValueRepository {
    Value findLastValueRecorded(SensorID sensorID);

}
