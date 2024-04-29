package com.ada.caldeiraoDoCrud.service;

import com.ada.caldeiraoDoCrud.model.CrudModel;
import com.ada.caldeiraoDoCrud.repository.CrudRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CrudServiceTest {

    @Mock
    private CrudRepository repository;
    @Mock
    private CrudModel crudModel;
    @InjectMocks
    private CrudService service;

    @Test
    void findAll() {

        CrudModel mocked = Mockito.mock(CrudModel.class);
        List<CrudModel> expected = List.of(crudModel, mocked);

        Mockito.when(repository.findAll())
                .thenReturn(expected);

        var actual = service.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findById() {

        var id = UUID.randomUUID();
        Optional<CrudModel> expected = Optional.of(crudModel);

        Mockito.when(repository.findById(id))
                .thenReturn(expected);

        var actual = service.findById(id);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByName() {

        var name = "Test";
        List<CrudModel> expected = List.of(crudModel);

        Mockito.when(repository.findByName(name))
                .thenReturn(expected);

        var actual = service.findByName(name);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByEmail() {

        var email = "mail@mail.com";
        List<CrudModel> expected = List.of(crudModel);

        Mockito.when(repository.findByEmail(email))
                .thenReturn(expected);

        var actual = service.findByEmail(email);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteById() {

        var id = UUID.randomUUID();

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(new CrudModel()));

        boolean response = service.deleteById(id);

        Mockito.verify(repository, Mockito.times(1))
                .deleteById(id);

        Assertions.assertTrue(response);
    }

    @Test
    void deleteByIdNotFound() {

        var id = UUID.randomUUID();

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.empty());

        boolean response = service.deleteById(id);

        Mockito.verify(repository, Mockito.never())
                .deleteById(Mockito.any());
        Assertions.assertFalse(response);
    }

    @Test
    void updateSuccess() {

        var id = UUID.randomUUID();
        var expected = "Item updated with id: " + id;

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(crudModel));

        CrudModel newItem = new CrudModel();
        newItem.setName("Test Name");
        newItem.setEmail("test@mail.com");
        newItem.setDescription("A description");

        var actual = service.update(id, newItem);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateNotFound() {

        var id = UUID.randomUUID();
        var expected = "Item with id " + id + " not found";

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.empty());

        CrudModel newItem = new CrudModel();
        newItem.setName("Test Not Found");
        newItem.setEmail("not@found.com");
        newItem.setDescription("A description");

        var actual = service.update(id, newItem);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void save() {

        Mockito.when(repository.save(crudModel))
                .thenReturn(crudModel);

        var actual = service.save(crudModel);

        Mockito.verify(repository, Mockito.times(1))
                .save(crudModel);

        Assertions.assertEquals(crudModel, actual);
    }
}