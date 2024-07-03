package smarthome.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Utility class for persisting entities using an EntityManager.
 */
public class EntityUpdater
{
    /**
     * Persists the provided entity using the given EntityManager.
     *
     * @param entity        The entity to persist.
     * @param entityManager The EntityManager used for persistence.
     * @return True if the entity is successfully persisted, otherwise false.
     */
    public <T> boolean persistEntity(T entity, EntityManager entityManager)
    {
        EntityTransaction entityTransaction;

        // Check if EntityManager is not null
        if (entityManager != null)
        {
            // Get the EntityTransaction from the EntityManager
            entityTransaction = entityManager.getTransaction();

            // Use try-with-resources to automatically close the EntityManager
            try (entityManager)
            {
                // Begin a transaction
                entityTransaction.begin();
                // Persist the entity
                entityManager.persist(entity);
                // Commit the transaction
                entityTransaction.commit();
                // Return true indicating successful persistence
                return true;
            } catch (Exception e)
            {
                // If an exception occurs, rollback the transaction
                if (entityTransaction.isActive())
                {
                    entityTransaction.rollback();
                }
            }
        }
        // Return false if EntityManager is null
        return false;
    }
}
