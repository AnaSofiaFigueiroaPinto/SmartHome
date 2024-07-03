package smarthome.persistence.repositoriesmem;

import smarthome.domain.repository.ValueRepository;
import smarthome.domain.value.Value;
import smarthome.domain.valueobjects.SensorID;

public interface InstantTimeLocationValueRepository extends ValueRepository {
    Value findLastValueRecorded(SensorID sensorID);
}
