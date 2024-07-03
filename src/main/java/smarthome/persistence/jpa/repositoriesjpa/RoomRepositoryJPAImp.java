package smarthome.persistence.jpa.repositoriesjpa;

import jakarta.persistence.*;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.persistence.jpa.datamodel.MapperRoomDataModel;
import smarthome.persistence.jpa.datamodel.RoomDataModel;
import smarthome.util.EntityUpdater;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class to represent a repository for rooms using JPA.
 */
public class RoomRepositoryJPAImp implements RoomRepository {
    /**
     * The factory for creating Room objects.
     */
    FactoryRoom factoryRoom;

    /**
     * Sensor data model used for updating reserved Room.
     */
    RoomDataModel roomDataModelForUpdate;

    /**
     * Entity Manager instance for managing persistence operations.
     */
    EntityManager entityManager;

    /**
     * Constructs a new RoomRepositoryJPAImp with the specified factory for creating room objects.
     *
     * @param factoryRoom the factory for creating room objects
     */
    public RoomRepositoryJPAImp(FactoryRoom factoryRoom, EntityManager entityManager)
    {
        this.factoryRoom = factoryRoom;
        this.entityManager = entityManager;
    }

    /**
     * Saves a room object to the database.
     *
     * @param room the room object to save
     * @return the saved room object
     * @throws IllegalArgumentException if the room object is null
     */
    public Room save(Room room)
    {
        if (room == null) {
            throw new IllegalArgumentException();
        }

        RoomDataModel roomDataModel = new RoomDataModel(room);

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        entityManager.persist(roomDataModel);
        entityTransaction.commit();
        entityManager.close();

        return room;
    }

    /**
     * Finds all room objects stored in the database.
     *
     * @return an Iterable containing all room objects stored in the database
     */
    public Iterable<Room> findAllEntities()
    {
        Query query = entityManager.createQuery(
                "SELECT e FROM RoomDataModel e");

        List<RoomDataModel> listDataModel = query.getResultList();
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();

        return mapperRoomDataModel.toDomainList(factoryRoom, listDataModel);
    }

    /**
     * Finds a room object in the database by its identity.
     *
     * @param roomID the identity of the room object to find
     * @return an Optional containing the room object if found, or empty otherwise
     */
    public Optional<Room> findEntityByID(RoomID roomID)
    {
        RoomDataModel roomDataModel = entityManager.find(RoomDataModel.class, roomID.toString());
        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        Room roomDomain = mapperRoomDataModel.toDomain(factoryRoom, roomDataModel);

        return Optional.ofNullable(roomDomain);

    }

    /**
     * Checks if a room object with the specified identity exists in the database.
     *
     * @param roomID the identity of the room object to check
     * @return true if a room object with the specified identity exists, false otherwise
     */
    public boolean containsEntityByID(RoomID roomID)
    {
        Optional<Room> optionalRoom = findEntityByID(roomID);
        return optionalRoom.isPresent();
    }

    /**
     * Updates the given room.
     *
     * @param room The room object to be updated.
     * @return The updated room if the operation is successful, or null if the room is not found or the update fails.
     */
    @Override
    public Room update(Room room) {
        RoomDataModel roomDataModel = entityManager.find(RoomDataModel.class, room.identity().toString());

        return roomUpdater(room, roomDataModel);
    }

    /**
     * Updates the Room object with data from the corresponding RoomDataModel and persists the changes.
     *
     * @param room           The Room object to be updated.
     * @param roomDataModel The corresponding RoomDataModel object containing updated data.
     * @return The updated Room object if the update is successful and persisted, otherwise null.
     */
    private Room roomUpdater(Room room, RoomDataModel roomDataModel) {
        if (roomDataModel != null){
            boolean isUpdated = roomDataModel.updateFromDomain(room);

            if (isUpdated){
                EntityUpdater entityUpdater = new EntityUpdater();
                if(entityUpdater.persistEntity(roomDataModel, entityManager))
                    return room;
            }
            else
                return null;
        }
        else
            return null;
        return null;
    }

    /**
     * Updates the reserved room.
     *
     * @param room The reserved room object to be updated.
     * @return The updated room if the operation is successful, or null if the room is not found or the update fails.
     */
    @Override
    public Room updateReserved(Room room) {
        return roomUpdater(room, roomDataModelForUpdate);
    }

    /**
     * Finds an entity by its ID and reserves it.
     *
     * @param roomID The ID of the room to find and reserve.
     * @return An optional containing the reserved room if found, or an empty optional if not found.
     */
    @Override
    public Optional<Room> findEntityByIDAndReserve(RoomID roomID) {
        RoomDataModel roomDataModel = entityManager.find(RoomDataModel.class, roomID.toString());

        if (roomDataModel != null){
            MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
            Room roomDomain = mapperRoomDataModel.toDomain(factoryRoom, roomDataModel);
            this.roomDataModelForUpdate = roomDataModel;
            return Optional.of(roomDomain);
        }
        else
            return Optional.empty();
    }

    /**
     * Finds all Room entities in a specified house.
     *
     * @param houseID The house ID to search for.
     * @return An Iterable containing all Room entities of the specified house.
     */
    @Override
    public Iterable<Room> findByHouseID(HouseID houseID) {
        Query query = Objects.requireNonNull(entityManager).createQuery("SELECT e FROM RoomDataModel e WHERE e.houseID = :houseID");
        query.setParameter("houseID", houseID);
        List<RoomDataModel> roomDataModelList = query.getResultList();

        MapperRoomDataModel mapperRoomDataModel = new MapperRoomDataModel();
        return mapperRoomDataModel.toDomainList(factoryRoom, roomDataModelList);
    }

}
