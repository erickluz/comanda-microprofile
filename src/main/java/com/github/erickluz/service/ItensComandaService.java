package com.github.erickluz.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.erickluz.domain.ItensComanda;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.repository.ItensComandaRepository;

@ApplicationScoped
public class ItensComandaService {

    @Inject
    ItensComandaRepository dao;

    public List<ItensComanda> buscarItensPorIdComanda(Long idComanda){
        return dao.buscarItensPorIdComanda(idComanda);
    }

    public ItensComanda salvar(ItensComanda itemComanda) {
        dao.persist(itemComanda);
        itemComanda.setIdItemComanda((long)dao.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(itemComanda));
        return itemComanda;
    }

    public void salvarItensComanda(List<ItensComanda> itensComandas) {
        dao.persist(itensComandas);
    }

    public void remover(Long id) {
        dao.deleteById(id);
    }

    public ItensComanda buscar(Long id) throws ObjectNotFoundException {
    	return dao.findById(id);
    }

    public ItensComanda buscarItemPorProdutoEComanda(Long idProduto, Long idComanda) {
        return dao.buscarItemPorProdutoEComanda(idProduto, idComanda);
    }
    
    public boolean isProdutoEmAlgumaComanda(Long idProduto) {
    	Long quantidadeDeComandas = dao.buscarItensPorIdProduto(idProduto);
    	return (quantidadeDeComandas.compareTo(0L) > 0);
    }

    public void excluirItensPorIdComanda(Long idComanda) {
        dao.delete("DELETE FROM ItensComanda ic WHERE ic.comanda.idComanda", idComanda);
    }
    
    public ItensComanda buscarItensComandaComProduto(long id) {
    	return dao.buscarItensComProduto(id);
    }
}