package pl.grzegorz.motorcycleservice.motorcycle_class.query;

import javax.persistence.*;

@Entity
@Table(name = "motorcycle_classes")
public class MotorcycleClassSimpleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    protected MotorcycleClassSimpleEntity() {}

    public MotorcycleClassSimpleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
