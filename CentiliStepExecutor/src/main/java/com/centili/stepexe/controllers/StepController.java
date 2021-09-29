package com.centili.stepexe.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centili.stepexe.dtos.StepDto;
import com.centili.stepexe.exceptions.NotFoundException;
import com.centili.stepexe.exceptions.StepExistsException;
import com.centili.stepexe.exceptions.StepNotExistsException;
import com.centili.stepexe.services.StepService;
import com.centili.stepexe.services.TransactionService;
import com.centili.stepexe.utils.PriceHolder;

@RestController
@RequestMapping({ "/centili/step" })
public class StepController {
	@Autowired
	private StepService stepService;
	@Autowired
	private TransactionService transactionService;

	@PostMapping
	ResponseEntity<?> saveStep(@RequestBody @Valid StepDto stepDto) throws StepExistsException {
		return ResponseEntity.ok().body(this.stepService.convertToDto(this.stepService.save(stepDto)));
	}

	@PostMapping({ "/{stepName}" })
	ResponseEntity<?> saveTransaction(@PathVariable("stepName") String stepName,
			@RequestBody @Valid PriceHolder priceHolder) throws StepNotExistsException {
		return ResponseEntity.ok().body(
				this.transactionService.convertToDto(this.transactionService.save(priceHolder.getPrice(), stepName)));
	}

	@GetMapping({ "/{stepName}/transactions" })
	ResponseEntity<?> load(@PathVariable("stepName") String stepName) throws NotFoundException {
		return ResponseEntity.ok().body(this.transactionService.getTransactionsByStep(stepName));
	}

}
