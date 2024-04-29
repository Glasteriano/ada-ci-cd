package com.ada.caldeiraoDoCrud.service;

import com.ada.caldeiraoDoCrud.repository.CrudRepository;
import com.ada.caldeiraoDoCrud.model.CrudModel;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CrudService {

    private final CrudRepository repository;

    public List<CrudModel> findAll() {
        return repository.findAll();
    }

    public Optional<CrudModel> findById(UUID id) {
        return repository.findById(id);
    }

    public List<CrudModel> findByName(String name) {
        return repository.findByName(name);
    }

    public List<CrudModel> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public boolean deleteById(UUID id) {

        return repository.findById(id)
                .map(item -> {
                    repository.deleteById(id);

                    return true;
                })
                .orElse(false);
    }

    public String update(UUID id, CrudModel itemUpdated) {

        Optional<CrudModel> itemToUpdate = repository.findById(id);

        if(itemToUpdate.isPresent()) {
            CrudModel item = itemToUpdate.get();

            item.setName(itemUpdated.getName());
            item.setEmail(itemUpdated.getEmail());
            item.setDescription(itemUpdated.getDescription());
            item.setTime(LocalDateTime.now());

            repository.save(item);

            return "Item updated with id: " + id;
        }
        else {
            return "Item with id " + id + " not found";
        }
    }

    @Transactional
    public CrudModel save(CrudModel crudModel) {
        return repository.save(crudModel);
    }
}
