package com.ada.caldeiraoDoCrud.repository;

import com.ada.caldeiraoDoCrud.model.CrudModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CrudRepository extends JpaRepository<CrudModel, UUID> {

    List<CrudModel> findByName(String name);
    List<CrudModel> findByEmail(String email);
}
