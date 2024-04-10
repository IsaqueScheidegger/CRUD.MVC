package com.marketplace.marketplacesystem.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.marketplacesystem.models.Produto;

@Repository
public interface ProdutosRepository extends JpaRepository<Produto, Integer>{
	
}
