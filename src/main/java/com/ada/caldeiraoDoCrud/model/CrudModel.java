package com.ada.caldeiraoDoCrud.model;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class CrudModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String email;
    private String description;

    @UpdateTimestamp
    private LocalDateTime time;

    public CrudModel(String name,String email, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.description = description;
    }
}
