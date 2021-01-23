package com.github.erickluz.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.github.erickluz.domain.Comanda;
import com.github.erickluz.domain.StatusComanda;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class ComandaRepository implements PanacheRepository<Comanda> {

	public Comanda buscarComandaPorId(Long idComanda) {
		return find("FROM Comanda c "
				+ " INNER FETCH JOIN ItensComanda ic ON ic.comanda.idComanda = c.idComanda "
				+ " INNER FETCH JOIN Produto p ON p.idProduto = ic.produto.idProduto "
				+ " WHERE c.idComanda = :id", Parameters.with("id", idComanda)).firstResult();
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
