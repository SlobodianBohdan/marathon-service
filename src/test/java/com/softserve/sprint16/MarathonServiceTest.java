package com.softserve.sprint16;

import com.softserve.sprint16.entity.Marathon;
import com.softserve.sprint16.exception.EntityNotFoundException;
import com.softserve.sprint16.repository.MarathonRepository;
import com.softserve.sprint16.service.MarathonService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MarathonServiceTest extends BaseDaoTest {

    private MarathonService marathonService;
    private MarathonRepository marathonRepository;

    private static Marathon createdMarathon1;
    private static Marathon createdMarathon2;
    private static Marathon createdMarathon3;

    @Autowired
    public MarathonServiceTest(MarathonService marathonService, MarathonRepository marathonRepository) {
        this.marathonService = marathonService;
        this.marathonRepository = marathonRepository;
    }


    @BeforeEach
    public void fillDataService() {

        //Create marathons
        createdMarathon1 = Marathon.builder().title("Java Marathon_1").build();
        createdMarathon2 = Marathon.builder().title("Java Marathon_2").build();
        createdMarathon3 = Marathon.builder().title("Java Marathon_3").build();

        marathonRepository.save(createdMarathon1);
        marathonRepository.save(createdMarathon2);
        marathonRepository.save(createdMarathon3);
    }

    @AfterEach
    public void clearData() {
        clearTables("marathon");
    }


    @Test
    public void checkAllMarathons() {
        List<Marathon> expected = Arrays.asList(createdMarathon1, createdMarathon2, createdMarathon3);
        List<Marathon> actual = marathonService.getAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkMarathonById() {
        Marathon expected = Marathon.builder().title("Java Marathon_2").build();
        marathonRepository.save(expected);
        Marathon actual = marathonService.getMarathonById(expected.getId());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkCreateOrUpdate() {
        Marathon expected = createdMarathon1;
        Marathon actual = marathonService.createOrUpdate(createdMarathon1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void checkDeleteMarathonById() {
        Marathon expected = Marathon.builder().title("Java Marathon_5").build();
        marathonRepository.save(expected);
        marathonService.deleteMarathonById(expected.getId());
        Assertions.assertThrows(EntityNotFoundException.class, () -> marathonService.getMarathonById(expected.getId()));
    }
}
