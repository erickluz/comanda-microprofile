package com.github.erickluz.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.github.erickluz.commons.Util;
import com.github.erickluz.domain.Comanda;
import com.github.erickluz.domain.ItensComanda;
import com.github.erickluz.domain.Produto;
import com.github.erickluz.domain.StatusComanda;
import com.github.erickluz.dto.ComandaItensDTO;
import com.github.erickluz.exception.AuthorizationException;
import com.github.erickluz.exception.DataIntegrityException;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.repository.ComandaRepository;

@Transactional
@ApplicationScoped
public class ComandaService {

	@Inject
	ComandaRepository dao;
	@Inject
	ItensComandaService itensComandaService;
	@Inject
	ProdutoService produtoService;
	@Inject
	UsuarioService usuarioService;
	@Inject
	Util util;

	public Comanda buscar(Long id) {
		return dao.findById(id);
	}

	public List<Comanda> listarTodasComandasPorUsuario(Long idUsuario) {
		return dao.listarTodasComandasPorUsuario(idUsuario);
	}

	public List<Comanda> listarTodasComandasAtivasPorUsuario(Long idUsuario) {
		return dao.listarTodasComandasPorUsuarioEStatus(idUsuario, StatusComanda.ABERTA);
	}

	public List<Comanda> listarTodasComandasFechadasPorUsuario(Long idUsuario) {
		return dao.listarTodasComandasPorUsuarioEStatus(idUsuario, StatusComanda.FECHADA);
	}

	public ComandaItensDTO buscarComandaCompletaPorId(Long id) throws ObjectNotFoundException {
		Comanda comanda = dao.buscarComandaPorId(id);
		if (comanda == null) {
			throw new ObjectNotFoundException("Comanda de id: " + id + " não encontrado");
		}
		List<ItensComanda> itens = itensComandaService.buscarItensPorIdComanda(comanda.getIdComanda());
		ComandaItensDTO comandaItens = new ComandaItensDTO(comanda, itens);
		return comandaItens;
	}

	public Comanda salvarComanda(ComandaItensDTO comanda) throws DataIntegrityException, ObjectNotFoundException, AuthorizationException {
		verificarNovaComanda(comanda);
		Comanda entidadeComanda = new Comanda(comanda.getNumero(), comanda.getNomeCliente(), comanda.getValorTotal());
		entidadeComanda.setHoraAbertura(LocalDateTime.now());
		entidadeComanda.setValorTotal(calcularValorTotal(comanda.getItensComanda()));
		entidadeComanda.setUsuario(usuarioService.buscar(comanda.getIdUsuario()));
		entidadeComanda.setStatus(StatusComanda.ABERTA);
		dao.persist(entidadeComanda);
		entidadeComanda.setIdComanda((Long) dao.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entidadeComanda));
		List<ItensComanda> itensComanda = comanda.getItensComanda();
		for (ItensComanda item : itensComanda) {
			item.setComanda(entidadeComanda);
		}
		itensComandaService.salvarItensComanda(itensComanda);
		return entidadeComanda;
	}

	private void verificarNovaComanda(ComandaItensDTO comanda) throws DataIntegrityException {
		if (comanda == null) {
			throw new DataIntegrityException("Comanda inválida");
		}
		if (comanda.getNumero() == null || comanda.getNumero().compareTo(0) <= 0) {
			throw new DataIntegrityException("Número da comanda invalida");
		}
		if (comanda.getIdUsuario() == null) {
			throw new DataIntegrityException("Usuário inválido");
		}
		if (buscarComandaPorNumero(comanda.getNumero(), comanda.getIdUsuario(), StatusComanda.ABERTA) != null) {
			throw new DataIntegrityException("O número da comanda informada ja está em uso");
		}
	}

	private Comanda buscarComandaPorNumero(Integer numeroComanda, Long idUsuario, StatusComanda statusComanda) {
		return dao.buscarComandaPorNumero(numeroComanda, idUsuario, statusComanda);
	}

	public void editarComanda(ComandaItensDTO comanda) throws DataIntegrityException, ObjectNotFoundException {
		verificarComanda(comanda);
		Comanda entidadeComanda = dao.buscarComandaPorId(comanda.getIdComanda());
		if (entidadeComanda == null) {
			throw new ObjectNotFoundException("Comanda não encontrada no sistema");
		}
		List<ItensComanda> itensComandas = new ArrayList<>();
		for (ItensComanda item : comanda.getItensComanda()) {
			ItensComanda entidadeItem = itensComandaService.buscarItemPorProdutoEComanda(item.getProduto().getIdProduto(), entidadeComanda.getIdComanda());
			if (entidadeItem == null) {
				entidadeItem = item;
			} else {
				entidadeItem.setQuantidade(item.getQuantidade());
			}
			entidadeItem.setComanda(entidadeComanda);
			itensComandas.add(entidadeItem);
		}
		entidadeComanda.setNomeCliente(comanda.getNomeCliente());
		entidadeComanda.setValorTotal(calcularValorTotal(comanda.getItensComanda()));
		dao.persist(entidadeComanda);
		itensComandaService.excluirItensPorIdComanda(entidadeComanda.getIdComanda());
		itensComandaService.salvarItensComanda(itensComandas);
	}

	private BigDecimal calcularValorTotal(List<ItensComanda> itens) throws ObjectNotFoundException {
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (ItensComanda item : itens) {
			Produto produto = produtoService.buscar(item.getProduto().getIdProduto());
			if (produto == null) {
				throw new ObjectNotFoundException("Produto informado não encontrado: " + item.getProduto().getIdProduto());
			}
			valorTotal = valorTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
		}
		return valorTotal;
	}

	private void verificarComanda(ComandaItensDTO comanda) throws DataIntegrityException {
		if (comanda == null) {
			throw new DataIntegrityException("Comanda vazia!");
		}
		for (ItensComanda itemComanda : comanda.getItensComanda()) {
			System.out.println(itemComanda.toString());
			if (itemComanda.getQuantidade() == null || itemComanda.getQuantidade().compareTo(0) < 0) {
				throw new DataIntegrityException("Item com quantidade inválida");
			}
		}
	}

	public void excluirComanda(Long idComanda) {
		itensComandaService.excluirItensPorIdComanda(idComanda);
		dao.excluirComandaPorId(idComanda);
	}

	/**
	 * Método criado para ADICIONAR itens a uma comanda que ja existe.
	 * @param comanda
	 * @throws DataIntegrityException
	 * @throws ObjectNotFoundException
	 */
	public void acrescentarItensEmUmaComanda(ComandaItensDTO comanda) throws DataIntegrityException, ObjectNotFoundException {
		verificarComanda(comanda);
		Comanda entidadeComanda = dao.buscarComandaPorId(comanda.getIdComanda());
		if (entidadeComanda == null) {
			throw new ObjectNotFoundException("Comanda não encontrada no sistema");
		}
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (ItensComanda item : comanda.getItensComanda()) {
			boolean isItemNaComanda = false;
			List<ItensComanda> itensDaComanda = itensComandaService.buscarItensPorIdComanda(entidadeComanda.getIdComanda());
			ItensComanda entidadeItemComanda;
			for (ItensComanda itemEntidade : itensDaComanda) {
				if (itemEntidade.getProduto().getIdProduto().equals(item.getProduto().getIdProduto())) {
					itemEntidade.setQuantidade(itemEntidade.getQuantidade() + item.getQuantidade());
					entidadeItemComanda = itensComandaService.salvar(itemEntidade);
					valorTotal = valorTotal.add(entidadeItemComanda.getProduto().getPreco().multiply(BigDecimal.valueOf(entidadeItemComanda.getQuantidade().doubleValue())));
					isItemNaComanda = true;
					break;
				}
			}
			if (!isItemNaComanda) {
				item.setComanda(entidadeComanda);
				itensComandaService.salvar(item);
				Produto produto = produtoService.buscar(item.getProduto().getIdProduto());
				valorTotal = valorTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade().doubleValue())));
			}
		}
		entidadeComanda.setValorTotal(valorTotal);
		dao.persist(entidadeComanda);
	}

	public void fecharComanda(Long idComanda) throws DataIntegrityException, ObjectNotFoundException {
		if (idComanda == null) {
			throw new DataIntegrityException("Comanda inválida");
		}
		Comanda comanda = dao.findById(idComanda);
		comanda.setStatus(StatusComanda.FECHADA);
		comanda.setHoraFechamento(LocalDateTime.now());
		dao.persist(comanda);
	}
}