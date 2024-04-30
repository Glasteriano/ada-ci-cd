package com.ada.caldeiraoDoCrud.controller;

import com.ada.caldeiraoDoCrud.model.CrudModel;
import com.ada.caldeiraoDoCrud.repository.CrudRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CrudControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CrudRepository repository;

    private String URL;

    @BeforeEach
    void setUp() {
        URL = "http://localhost:" + port + "/crud";

        var crudTeam1 = CrudModel.builder()
                .id(UUID.randomUUID())
                .name("Vinícius Maschio")
                .email("maschio@test.com")
                .description("The man to be beaten")
                .time(LocalDateTime.now())
                .build();

        var crudTeam2 = CrudModel.builder()
                .id(UUID.randomUUID())
                .name("Rafael Porto")
                .email("porto@test.com")
                .description("The one who speaks norwegian")
                .time(LocalDateTime.now())
                .build();

        repository.save(crudTeam1);
        repository.save(crudTeam2);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void findAll() {

        var response = restTemplate.getForEntity(URL, CrudModel[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(repository.count(), Objects.requireNonNull(response.getBody()).length);
    }

    @Test
    void findByIdSuccess() {

        var entity = repository.findAll().get(0);
        var id = entity.getId();

        var response = restTemplate.getForEntity(URL + "/{id}", CrudModel.class, id);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(entity, response.getBody());
    }

    @Test
    void findByIdNotFound() {

        var id = UUID.randomUUID();

        var response = restTemplate.getForEntity(URL + "/{id}", CrudModel.class, id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void findByNameSuccess() {

        var name = "Vinícius Maschio";
        var entity = repository.findByName(name);

        var response = restTemplate.getForEntity(URL + "/name/{name}", CrudModel[].class, name);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(entity.stream().count(), Objects.requireNonNull(response.getBody()).length);
    }

    @Test
    void findByNameEmpty() {

        var name = "Cristiano Ronaldo";
        var entity = repository.findByName(name);

        var response = restTemplate.getForEntity(URL + "/name/{name}", CrudModel[].class, name);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(entity.stream().count(), Objects.requireNonNull(response.getBody()).length);
    }

    @Test
    void findByEmailSuccess() {

        var email = "porto@test.com";
        var entity = repository.findByEmail(email);

        var response = restTemplate.getForEntity(URL + "/email/{email}", CrudModel[].class, email);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(entity.stream().count(), Objects.requireNonNull(response.getBody()).length);
    }

    @Test
    void findByEmailEmpty() {

        var email = "dont@have.com";
        var entity = repository.findByEmail(email);

        var response = restTemplate.getForEntity(URL + "/email/{email}", CrudModel[].class, email);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(entity.stream().count(), Objects.requireNonNull(response.getBody()).length);
    }

    @Test
    void save() {

        var newItem = CrudModel.builder()
                .name("Cristiano Ronaldo")
                .email("siii@mail.com")
                .description("Eu sou o milior")
                .build();

        var response = restTemplate.postForEntity(URL, newItem, CrudModel.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(newItem.getName(), response.getBody().getName());
        Assertions.assertEquals(newItem.getEmail(), response.getBody().getEmail());
        Assertions.assertNotNull(response.getBody().getId());
    }

    @Test
    void deleteSuccess() {

        var id = repository.findAll().get(0).getId();

        var response = restTemplate.exchange(URL + "/{id}",
                HttpMethod.DELETE, null, Void.class, id);

        var entity = repository.findById(id);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertFalse(entity.isPresent());
    }

    @Test
    void deleteNotFound() {

        var id = UUID.randomUUID();

        var response = restTemplate.exchange(URL + "/{id}",
                HttpMethod.DELETE, null, Void.class, id);

        var entity = repository.findById(id);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertFalse(entity.isPresent());
    }
}
