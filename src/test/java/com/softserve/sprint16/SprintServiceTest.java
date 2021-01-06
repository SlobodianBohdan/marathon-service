package com.softserve.sprint16;

import com.softserve.sprint16.entity.Marathon;
import com.softserve.sprint16.entity.Sprint;
import com.softserve.sprint16.repository.SprintRepository;
import com.softserve.sprint16.service.MarathonService;
import com.softserve.sprint16.service.SprintService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SprintServiceTest extends BaseDaoTest {

    private SprintService sprintService;
    private SprintRepository sprintRepository;
    private MarathonService marathonService;

    private static Sprint createdSprint1;
    private static Sprint createdSprint2;
    private static Sprint createdSprint3;
    private static Marathon createdMarathon;

    @Autowired
    public SprintServiceTest(SprintService sprintService, SprintRepository sprintRepository, MarathonService marathonService) {
        this.sprintService = sprintService;
        this.sprintRepository = sprintRepository;
        this.marathonService = marathonService;
    }


    @BeforeEach
    public void fillDataService() {

        Sprint sprint1 = Sprint.builder()
                .title("Sprint 1")
                .build();
        Sprint sprint2 = Sprint.builder()
                .title("Sprint 2")
                .build();
        Sprint sprint3 = Sprint.builder()
                .title("Sprint 3")
                .build();

        createdSprint1 = sprintRepository.save(sprint1);
        createdSprint2 = sprintRepository.save(sprint2);
        createdSprint3 = sprintRepository.save(sprint3);

        Marathon marathon1 = Marathon.builder().title("Java Marathon_1").build();

        createdMarathon = marathonService.createOrUpdate(marathon1);



    }

    @AfterEach
    public void clearData() {
        clearTables("sprint", "marathon");
    }

    @Test
    public void checkSprintsByMarathonId() {
        List<Sprint> expected = Arrays.asList(createdSprint1, createdSprint2, createdSprint3);
        sprintService.addSprintToMarathon(createdSprint1, createdMarathon);
        sprintService.addSprintToMarathon(createdSprint2, createdMarathon);
        sprintService.addSprintToMarathon(createdSprint3, createdMarathon);
        List<Sprint> actual = sprintService.getSprintsByMarathonId(createdMarathon.getId());

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkAddSprintToMarathon() {
        List<Marathon> expected = Arrays.asList(createdMarathon);
        sprintService.addSprintToMarathon(createdSprint1, createdMarathon);
        List<Marathon> actual = Arrays.asList(createdSprint1.getMarathon());

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkUpdateSprint() {
        List<Sprint> expected = Arrays.asList(createdSprint1, createdSprint2, createdSprint3);
        sprintService.updateSprint(createdSprint1);
        sprintService.updateSprint(createdSprint2);
        sprintService.updateSprint(createdSprint3);
        List<Sprint> actual = Arrays.asList(sprintService.getSprintById(createdSprint1.getId()),
                sprintService.getSprintById(createdSprint2.getId()),
                sprintService.getSprintById(createdSprint3.getId()));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkSprintById() {
        List<Sprint> expected = Arrays.asList(createdSprint1);
        List<Sprint> actual = Arrays.asList(sprintService.getSprintById(createdSprint1.getId()));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void checkCreate() {
        List<Sprint> expected = Arrays.asList(createdSprint1);
        List<Sprint> actual = Arrays.asList(sprintService.create(createdSprint1));

        Assertions.assertEquals(expected.toString(), actual.toString());
    }
}


