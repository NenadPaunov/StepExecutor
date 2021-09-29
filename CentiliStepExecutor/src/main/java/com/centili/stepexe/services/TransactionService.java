package com.centili.stepexe.services;

import java.util.List;

import com.centili.stepexe.dtos.TransactionDto;
import com.centili.stepexe.entities.Step;
import com.centili.stepexe.entities.Transaction;
import com.centili.stepexe.exceptions.NotFoundException;
import com.centili.stepexe.exceptions.StepNotExistsException;

public interface TransactionService extends BaseService<TransactionDto, Transaction, Integer> {

	List<Transaction> findByStep(Step step);

	Transaction save(double price, String stepName) throws StepNotExistsException;

	List<TransactionDto> getTransactionsByStep(String stepName) throws NotFoundException;
}
