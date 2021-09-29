package com.centili.stepexe.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.centili.stepexe.entities.Step;
import com.centili.stepexe.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByStep(Step step);

}
