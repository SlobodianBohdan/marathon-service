package com.softserve.sprint16.service;

import com.softserve.sprint16.entity.Marathon;
import com.softserve.sprint16.exception.EntityNotFoundException;
import com.softserve.sprint16.repository.MarathonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarathonServiceImpl implements MarathonService {

    private MarathonRepository marathonRepository;

    @Autowired
    public MarathonServiceImpl(MarathonRepository marathonRepository) {
        this.marathonRepository = marathonRepository;
    }

    @Override
    public List<Marathon> getAll() {
        List<Marathon> marathons = marathonRepository.findAll();
        return marathons.isEmpty() ? new ArrayList<>() : marathons;
    }

    @Override
    public Marathon getMarathonById(Long id) {

        return marathonRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(("No marathon exist for given id " + id)));

    }

    @Override
    public Marathon createOrUpdate(Marathon marathon) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<Marathon>> violations = validator.validate(marathon);
//        if (!violations.isEmpty()) {
//            throw new RuntimeException(violations.toString());
//        }

        if (marathon.getId() != null) {

            Optional<Marathon> marathonOptional = marathonRepository.findById(marathon.getId());

            if (marathonOptional.isPresent()) {
                Marathon newMarathon = marathonOptional.get();
                newMarathon.setTitle(marathon.getTitle());
                return marathonRepository.save(newMarathon);
            }
        }

        return marathonRepository.save(marathon);

    }

    @Override
    public void deleteMarathonById(Long id) {
        Optional<Marathon> marathon = marathonRepository.findById(id);
        if (marathon.isPresent()) {
            marathon.get().getUsers().clear();
            marathonRepository.save(marathon.get());
            marathonRepository.delete(marathon.get());
        } else throw new EntityNotFoundException("No user exist for given id");
    }
}
