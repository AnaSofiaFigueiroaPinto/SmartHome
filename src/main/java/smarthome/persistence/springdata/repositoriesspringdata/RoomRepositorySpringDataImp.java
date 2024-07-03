package smarthome.persistence.springdata.repositoriesspringdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import smarthome.domain.repository.RoomRepository;
import smarthome.domain.room.FactoryRoom;
import smarthome.domain.room.Room;
import smarthome.domain.valueobjects.HouseID;
import smarthome.domain.valueobjects.RoomID;
import smarthome.persistence.jpa.datamodel.MapperRoomDataModel;
import smarthome.persistence.jpa.datamodel.RoomDataModel;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("!test")
/**
 * Implementation of the RoomRepository interface using Spring Data JPA.
 */
public class RoomRepositorySpringDataImp implements RoomRepository {
    /**
     * The repository for accessing room data managed by Spring Data.
     */
    RoomRepositorySpringData roomRepositorySpringDataInt;

    /**
     * Factory for creating Room entities.
     */
    FactoryRoom factoryRoom;

    /**
     * Room data model used for updating reserved rooms.
     */
    RoomDataModel roomDataModelForUpdate;

    /**
     * Mapper for converting RoomDataModel to Room.
     */
    MapperRoomDataModel mapperRoomDataModel;


    /**
     * Constructs a new ImpRoomRepositorySpringData with the specified RepositoryRoomSpringData and FactoryRoom instances.
     *
     * @param roomRepositorySpringDataInt The RepositoryRoomSpringData instance to interact with the room repository.
     * @param factoryRoom                 The FactoryRoom instance to create room objects.
     */
    public RoomRepositorySpringDataImp(RoomRepositorySpringData roomRepositorySpringDataInt, FactoryRoom factoryRoom, MapperRoomDataModel mapperRoomDataModel) {
        this.roomRepositorySpringDataInt = roomRepositorySpringDataInt;
        this.factoryRoom = factoryRoom;
        this.mapperRoomDataModel = mapperRoomDataModel;
    }

    /**
     * Saves the specified room to the repository.
     *
     * @param room The room to save.
     * @return The saved room.
     */
    @Override
    public Room save(Room room) {
        if (room == null) {
            throw new IllegalArgumentException();
        }
        RoomDataModel roomDataModel = new RoomDataModel(room);
        this.roomRepositorySpringDataInt.save(roomDataModel);
        return room;
    }

    /**
     * Retrieves all Room entities from the repository.
     *
     * @return An Iterable containing all Room entities.
     */
    @Override
    public Iterable<Room> findAllEntities() {
        List<RoomDataModel> listRoomDataModelSaved = this.roomRepositorySpringDataInt.findAll();
        return mapperRoomDataModel.toDomainList(factoryRoom, listRoomDataModelSaved);
    }

    /**
     * Retrieves a Room entity with the specified RoomID from the repository.
     *
     * @param roomID The RoomID of the room to retrieve.
     * @return The Room entity if found, null otherwise.
     */
    @Override
    public Optional<Room> findEntityByID(RoomID roomID) {
        Optional<RoomDataModel> roomDataModel = this.roomRepositorySpringDataInt.findById(roomID.toString());

        if (roomDataModel.isPresent()) {
            Room room = mapperRoomDataModel.toDomain(factoryRoom, roomDataModel.get());
            return Optional.of(room);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks if a Room entity with the specified RoomID exists in the repository.
     *
     * @param roomID The RoomID to check for.
     * @return True if the Room entity exists, false otherwise.
     */
    @Override
    public boolean containsEntityByID(RoomID roomID) {
        return this.roomRepositorySpringDataInt.existsById(roomID.toString());
    }

    /**
     * Updates a Room entity in the repository.
     *
     * @param room The Sensor entity to update.
     * @return The updated Room entity if successful, null otherwise.
     */
    @Override
    public Room update(Room room) {
        Optional<RoomDataModel> roomDataModel = roomRepositorySpringDataInt.findById(room.identity().toString());

        if (roomDataModel.isPresent()) {
            var roomDataModelExisting = roomDataModel.get();
            boolean isUpdated = roomDataModelExisting.updateFromDomain(room);

            if (isUpdated) {
                this.roomRepositorySpringDataInt.save(roomDataModelExisting);
                return room;
            } else
                return null;
        } else
            return null;
    }

    /**
     * Updates a reserved Room entity in the repository.
     *
     * @param room The Room entity with reserved updates to apply.
     * @return The updated Room entity if successful, null otherwise.
     */
    @Override
    public Room updateReserved(Room room) {
        if (roomDataModelForUpdate != null) {
            boolean isUpdated = roomDataModelForUpdate.updateFromDomain(room);

            if (isUpdated) {
                this.roomRepositorySpringDataInt.save(roomDataModelForUpdate);
                return room;
            } else
                return null;
        } else
            return null;
    }

    /**
     * Retrieves all Room entities from the repository.
     *
     * @param roomID The RoomID of the room to retrieve.
     * @return An Iterable containing all Room entities.
     */
    @Override
    public Optional<Room> findEntityByIDAndReserve(RoomID roomID) {
        Optional<RoomDataModel> roomDataModel = this.roomRepositorySpringDataInt.findById(roomID.toString());

        if (roomDataModel.isPresent()) {
            Room room = mapperRoomDataModel.toDomain(factoryRoom, roomDataModel.get());
            this.roomDataModelForUpdate = roomDataModel.get();
            return Optional.of(room);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all Room entities from the repository of a given houseID.
     *
     * @param houseID The HouseID of the house to retrieve.
     * @return An Iterable containing all Room entities.
     */
    @Override
    public Iterable<Room> findByHouseID (HouseID houseID) {
        Iterable<RoomDataModel> roomDataModelList = roomRepositorySpringDataInt.findAllByHouseID(houseID.toString());
        return mapperRoomDataModel.toDomainList(factoryRoom, roomDataModelList);
    }
}
