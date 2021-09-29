package com.centili.stepexe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import org.modelmapper.ModelMapper;

import com.centili.stepexe.dtos.StepDto;
import com.centili.stepexe.entities.Step;
import com.centili.stepexe.exceptions.StepExistsException;
import com.centili.stepexe.repositories.StepRepository;
import com.centili.stepexe.utils.StepExecution;

@Service
public class StepServiceImpl implements StepService {
	@Autowired
	private StepRepository stepRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Optional<Step> findByName(String name) {
		return this.stepRepository.findByName(name);
	}

	@Override
	public Step save(StepDto dto) throws StepExistsException {
		Optional<Step> stepOptional = findByName(dto.getName());
		if (!stepOptional.isPresent()) {
			return stepRepository.save(convertToEntity(dto));
		}
		throw new StepExistsException();
	}

	@Override
	public Step convertToEntity(StepDto dto) {
		if (dto == null) {
			return null;
		}
		return this.modelMapper.map(dto, Step.class);
	}

	@Override
	public StepDto convertToDto(Step entity) {
		if (entity == null) {
			return null;
		}
		return this.modelMapper.map(entity, StepDto.class);
	}

	@Override
	public StepExecution executeStep(Step step, double price) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String url = step.getUrl() + "?price=" + price + "&stepName=" + step.getName();
		StepExecution stepExecution = new StepExecution();
		try {
			stepExecution = rest.exchange(url, HttpMethod.GET, entity, StepExecution.class).getBody();
			if (stepExecution == null) {
				stepExecution = new StepExecution();
				stepExecution.setStatus("FAILURE");
				return stepExecution;
			}
			return stepExecution;
		} catch (HttpClientErrorException ex) {
			stepExecution.setStatus("FAILURE");
			return stepExecution;
		}

	}

}
