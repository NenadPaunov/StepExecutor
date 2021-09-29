package com.centili.stepexe.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centili.stepexe.entities.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {

	Optional<Step> findByName(String name);

}
