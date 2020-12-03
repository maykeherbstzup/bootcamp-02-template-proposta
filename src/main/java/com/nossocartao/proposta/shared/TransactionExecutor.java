package com.nossocartao.proposta.shared;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.function.Supplier;

@Component
public class TransactionExecutor {
    @PersistenceContext
	private EntityManager manager;

	@Transactional
	public <T> T saveAndCommit(T obj) {
		manager.persist(obj);
		return obj;
	}

	@Transactional
	public <T> T updateAndCommit(T obj) {
		return manager.merge(obj);
    }

    @Transactional
    public <T> T execute(Supplier<T> someCode){
        return someCode.get();
    }
}
