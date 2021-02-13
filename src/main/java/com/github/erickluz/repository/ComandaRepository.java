package com.github.erickluz.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.github.erickluz.domain.Comanda;
import com.github.erickluz.domain.StatusComanda;
import com.github.erickluz.dto.ComandaItensDTO;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ComandaRepository implements PanacheRepository<Comanda> {
	
	@Inject
	private BaseDao dao;
	
	public Comanda buscarComandaPorId(Long idComanda) {
		return findById(idComanda);
	}
	
	public ComandaItensDTO buscarComandaCompletaPorId(Long idComanda) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", idComanda);
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT new com.github.erickluz.dto.ComandaItensDTO(c, ic) FROM Comanda c ")
			 .append("		LEFT JOIN FETCH ItensComanda ic ON ic.comanda.idComanda = c.idComanda ")
			 .append("		LEFT JOIN FETCH Produto p ON p.idProduto = ic.produto.idProduto ")
			 .append("		WHERE c.idComanda = :id");
		
		return (ComandaItensDTO) dao.find(query.toString(), params);
	}

	@Transactional
	public void excluirComandaPorId(Long id) {
		deleteById(id);
	}

	public List<Comanda> listarTodasComandasPorUsuario(Long idUsuario) {
		return list("FROM Comanda c WHERE c.usuario.idUsuario = ?1", idUsuario);
	}

	public Comanda buscarComandaPorNumero(Integer numeroComanda, Long idUsuario, StatusComanda status) {
		Map<String, Object> params = new HashMap<>();
		params.put("numeroComanda", numeroComanda);
		params.put("idUsuario", idUsuario);
		params.put("status", status);
		return find("FROM Comanda c WHERE c.numero = :numeroComanda AND c.usuario.idUsuario = :idUsuario AND c.status = :status", params).firstResult();
	}

	public List<Comanda> listarTodasComandasPorUsuarioEStatus(Long idUsuario, StatusComanda status) {
		Map<String, Object> params = new HashMap<>();
		params.put("idUsuario", idUsuario);
		params.put("status", status);
		return list("FROM Comanda c WHERE c.usuario.idUsuario = :idUsuario AND c.status = :status", params);
	}
}
