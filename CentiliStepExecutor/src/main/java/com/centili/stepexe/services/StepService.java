package com.centili.stepexe.services;

import java.util.Optional;

import com.centili.stepexe.dtos.StepDto;
import com.centili.stepexe.entities.Step;
import com.centili.stepexe.exceptions.StepExistsException;
import com.centili.stepexe.utils.StepExecution;

public interface StepService extends BaseService<StepDto, Step, Integer> {

	Optional<Step> findByName(String name);

	Step save(StepDto dto) throws StepExistsException;

	StepExecution executeStep(Step step, double price);
}
