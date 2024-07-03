package smarthome.ddd;

public interface DomainEntity<ID extends DomainID> {

    ID identity();

    boolean isSameAs(Object object);
}
