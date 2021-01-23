package com.github.erickluz.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.github.erickluz.domain.ItensComanda;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class ItensComandaRepository implements PanacheRepository<ItensComanda>{

    public List<ItensComanda> buscarItensPorIdComanda(Long idComanda) {
    	return list("FROM ItensComanda ic WHERE ic.comanda.idComanda = ?1", idComanda);
    }

    public ItensComanda buscarItemPorProdutoEComanda(Long idProduto, Long idComanda) {
    	return find("FROM ItensComanda ic WHERE ic.produto.idProduto = :idProduto AND ic.comanda.idComanda = :idComanda", 
    			Parameters.with("idProduto", idProduto),
    			Parameters.with("idComanda", idComanda))
    			.firstResult();
    }

    public void excluirItensPorIdComanda(Long idComanda) {
    	delete("FROM ItensComanda ic WHERE ic.comanda.idComanda = ?1", idComanda);
    }
    
	public Long buscarItensPorIdProduto(Long idProduto) {
		return count("FROM ItensComanda ic WHERE ic.produto.idProduto = ?1", idProduto);
	}
}