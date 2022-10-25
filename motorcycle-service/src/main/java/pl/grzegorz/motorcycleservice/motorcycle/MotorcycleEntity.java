package pl.grzegorz.motorcycleservice.motorcycle;

import org.hibernate.annotations.GenericGenerator;
import pl.grzegorz.motorcycleservice.motorcycle_class.query.MotorcycleClassSimpleEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "motorcycles")
class MotorcycleEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String brand;
    private String model;
    private int capacity;
    private int horsePower;
    private int vintage;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private MotorcycleClassSimpleEntity motorcycleClass;
    private String bikerId;

    MotorcycleEntity(String brand, String model, int capacity, int horsePower, int vintage, MotorcycleClassSimpleEntity motorcycleClass, String bikerId) {
        this.brand = brand;
        this.model = model;
        this.capacity = capacity;
        this.horsePower = horsePower;
        this.vintage = vintage;
        this.motorcycleClass = motorcycleClass;
        this.bikerId = bikerId;
    }

    protected MotorcycleEntity() {}

    UUID getId() {
        return id;
    }

    void setId(UUID id) {
        this.id = id;
    }

    String getBrand() {
        return brand;
    }

    void setBrand(String brand) {
        this.brand = brand;
    }

    String getModel() {
        return model;
    }

    void setModel(String model) {
        this.model = model;
    }

    int getCapacity() {
        return capacity;
    }

    void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    int getHorsePower() {
        return horsePower;
    }

    void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    int getVintage() {
        return vintage;
    }

    void setVintage(int vintage) {
        this.vintage = vintage;
    }

    MotorcycleClassSimpleEntity getMotorcycleClass() {
        return motorcycleClass;
    }

    void setMotorcycleClass(MotorcycleClassSimpleEntity motorcycleClass) {
        this.motorcycleClass = motorcycleClass;
    }

    String getBikerId() {
        return bikerId;
    }

    void setBikerId(String bikerId) {
        this.bikerId = bikerId;
    }
}
