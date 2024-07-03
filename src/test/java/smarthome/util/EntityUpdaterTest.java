package smarthome.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import static org.mockito.Mockito.*;

/**
 * Test class for EntityUpdater.
 */
class EntityUpdaterTest {

    /**
     * Test case for the persistEntity method with successful persistence.
     */
    @Test
    void persistEntitySuccess() {
        // Mock EntityManager and EntityTransaction
        EntityManager entityManager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        // Create an entity to persist
        Object entity = new Object();

        // Create an instance of EntityUpdater
        EntityUpdater entityUpdater = new EntityUpdater();

        // Call persistEntity method
        boolean result = entityUpdater.persistEntity(entity, entityManager);

        // Verify that transaction methods are called
        verify(transaction).begin();
        verify(entityManager).persist(entity);
        verify(transaction).commit();

        // Assert the result
        assertTrue(result);
    }

    /**
     * Test case for the persistEntity method when persistence fails.
     */
    @Test
    void persistEntityFailure() {
        // Mock EntityManager and EntityTransaction
        EntityManager entityManager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        // Create an entity to persist
        Object entity = new Object();

        // Stubbing EntityManager to throw an exception
        doThrow(new RuntimeException()).when(entityManager).persist(entity);

        // Stubbing transaction to indicate that it is active
        when(transaction.isActive()).thenReturn(true);

        // Create an instance of EntityUpdater
        EntityUpdater entityUpdater = new EntityUpdater();

        // Call persistEntity method
        boolean result = entityUpdater.persistEntity(entity, entityManager);

        // Verify that transaction rollback is called
        verify(transaction).isActive();
        verify(transaction).rollback();

        // Assert the result
        assertFalse(result);
    }

    /**
     * Test case for the persistEntity method with a null EntityManager.
     */
    @Test
    void persistEntityNullEntityManager() {
        // Create an entity to persist
        Object entity = new Object();

        // Create an instance of EntityUpdater
        EntityUpdater entityUpdater = new EntityUpdater();

        // Call persistEntity method with null EntityManager
        boolean result = entityUpdater.persistEntity(entity, null);

        // Assert the result
        assertFalse(result);
    }

    /**
     * Test case for the persistEntity method when an exception is caught and the transaction is not active.
     */
    @Test
    void persistEntityExceptionCaughtAndTransactionNotActive() {
        // Mock EntityManager and EntityTransaction
        EntityManager entityManager = mock(EntityManager.class);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(entityManager.getTransaction()).thenReturn(transaction);

        // Create an entity to persist
        Object entity = new Object();

        // Stubbing EntityManager to throw an exception
        doThrow(new RuntimeException()).when(entityManager).persist(entity);

        // Stubbing transaction to indicate that it is not active
        when(transaction.isActive()).thenReturn(false);

        // Create an instance of EntityUpdater
        EntityUpdater entityUpdater = new EntityUpdater();

        // Call persistEntity method
        boolean result = entityUpdater.persistEntity(entity, entityManager);

        // Verify that transaction rollback is not called
        verify(transaction, never()).rollback();

        // Assert the result
        assertFalse(result);
    }
}
