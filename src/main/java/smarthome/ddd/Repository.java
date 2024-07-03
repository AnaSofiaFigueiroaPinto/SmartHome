package smarthome.ddd;

import java.util.Optional;

public interface Repository<ID extends DomainID, T extends AggregateRoot<ID>> {
    /**
     * Finds an entity in the repository by its unique identifier.
     *
     * @param id The unique identifier of the entity to find.
     * @return An Optional containing the found entity, or empty if not found.
     */
    Optional<T> findEntityByID(ID id);

    /**
     * Retrieves all entity objects from the repository.
     * @return an iterable collection of entity objects
     */
    Iterable<T> findAllEntities();

    /**
     * Checks whether an entity with the given identifier exists in the repository.
     *
     * @param id The unique identifier of the entity to check.
     * @return true if the entity exists in the repository, false otherwise.
     */
    boolean containsEntityByID(ID id);
}
