package com.centili.stepexe.services;

public interface BaseService<D, T, ID> {

	T convertToEntity(D dto);

	D convertToDto(T entity);
}
