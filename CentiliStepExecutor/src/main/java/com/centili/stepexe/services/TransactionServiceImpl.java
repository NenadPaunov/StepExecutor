package com.centili.stepexe.services;

import org.modelmapper.ModelMapper;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centili.stepexe.dtos.TransactionDto;
import com.centili.stepexe.entities.Step;
import com.centili.stepexe.entities.Transaction;
import com.centili.stepexe.exceptions.NotFoundException;
import com.centili.stepexe.exceptions.StepNotExistsException;
import com.centili.stepexe.repositories.TransactionRepository;
import com.centili.stepexe.utils.ExceptionConstants;
import com.centili.stepexe.utils.StepExecution;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private StepService stepService;

	@Override
	public Transaction save(double price, String stepName) throws StepNotExistsException {
		Optional<Step> stepOptional = this.stepService.findByName(stepName);
		if (stepOptional.isPresent()) {
			return transactionRepository.save(createTransaction(price, stepOptional.get(),
					this.stepService.executeStep(stepOptional.get(), price)));
		}
		throw new StepNotExistsException();

	}

	@Override
	public Transaction convertToEntity(TransactionDto dto) {
		if (dto == null) {
			return null;
		}
		Transaction entity = this.modelMapper.map(dto, Transaction.class);
		return entity;
	}

	@Override
	public TransactionDto convertToDto(Transaction entity) {
		if (entity == null) {
			return null;
		}
		TransactionDto dto = this.modelMapper.map(entity, TransactionDto.class);
		return dto;
	}

	private Transaction createTransaction(double price, Step step, StepExecution stepExecution) {
		Transaction entity = new Transaction();
		entity.setPrice(price);
		entity.setStatus(stepExecution.getStatus());
		entity.setTimestamp(new Timestamp(System.currentTimeMillis()));
		entity.setStep(step);
		return entity;
	}

	@Override
	public List<Transaction> findByStep(Step step) {
		return transactionRepository.findByStep(step);
	}

	public List<TransactionDto> getTransactionsByStep(String stepName) throws NotFoundException {
		Optional<Step> stepOptional = stepService.findByName(stepName);
		if (stepOptional.isPresent()) {
			List<Transaction> transactions = this.findByStep(stepOptional.get());
			if (transactions.size() == 0) {
				throw new NotFoundException(ExceptionConstants.TRANSACTIONS_NOT_FOUND.getCode());
			}
			return transactions.stream().map(transaction -> this.convertToDto(transaction))
					.collect(Collectors.toList());
		}
		throw new NotFoundException(ExceptionConstants.STEP_NOT_EXIST.getCode());
	}
}
