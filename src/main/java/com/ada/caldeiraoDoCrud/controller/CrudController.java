package com.ada.caldeiraoDoCrud.controller;

import com.ada.caldeiraoDoCrud.service.CrudService;
import com.ada.caldeiraoDoCrud.model.CrudModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

@RequestMapping("/crud")
@RequiredArgsConstructor
@RestController
@Slf4j
public class CrudController {

    private final CrudService service;

    @GetMapping
    public ResponseEntity<List<CrudModel>> findAll(){

        log.info("findAll successfully executed!");

        List<CrudModel> crudModelList = service.findAll();

        return ResponseEntity.ok(crudModelList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CrudModel>> findById(@PathVariable("id") UUID id) {

        log.info("findById successfully executed!");

        Optional<CrudModel> crudModelOptional = service.findById(id);

        if(crudModelOptional.isPresent()) {
            return ResponseEntity.ok(crudModelOptional);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<CrudModel>> findByName(@PathVariable("name") String name) {

        log.info("findByName successfully executed!");

        List<CrudModel> crudModelOptional = service.findByName(name).stream().toList();

        return ResponseEntity.ok(crudModelOptional);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<CrudModel>> findByEmail(@PathVariable("email") String email) {

        log.info("findByEmail successfully executed!");

        List<CrudModel> crudModelOptional = service.findByEmail(email).stream().toList();

        return ResponseEntity.ok(crudModelOptional);
    }

    @PostMapping
    public ResponseEntity<CrudModel> save(@RequestBody CrudModel crudModel) {

        log.info("save successfully executed!");

        CrudModel response = service.save(crudModel);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") UUID id, @RequestBody CrudModel request) {

        log.info("update successfully executed!");

        String itemUpdated = service.update(id, request);

        return ResponseEntity.ok(itemUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") UUID id) {

        log.info("deleteById successfully executed!");

        boolean response = service.deleteById(id);

        return (response) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
