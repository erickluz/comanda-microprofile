package com.github.erickluz.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.github.erickluz.domain.Produto;
import com.github.erickluz.dto.ProdutoDTO;
import com.github.erickluz.exception.AuthorizationException;
import com.github.erickluz.exception.DataIntegrityException;
import com.github.erickluz.exception.ObjectNotFoundException;
import com.github.erickluz.repository.ProdutoRepository;

@Transactional
@ApplicationScoped
public class ProdutoService {
    
    @Inject
    ProdutoRepository dao;
    @Inject
    ItensComandaService itensComandaService;
    @Inject
    UsuarioService usuarioService;

    public Produto buscar(Long id) throws ObjectNotFoundException {
    	Produto produto = dao.findById(id);
    	if (produto == null) {
    		throw new ObjectNotFoundException("Produto do id " + id + " não encontrado");
    	}
        return produto;
    }

    public List<Produto> listarProdutosPorUsuario(Long idUsuario){
        return dao.listarTodosProdutosPorUsuario(idUsuario);
    }

    public Produto salvar(Produto produto) throws DataIntegrityException {
    	if (produto == null) {
    		throw new DataIntegrityException("Produto invalido");
    	}
        dao.persist(produto);
        produto.setIdProduto((Long) dao.getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(produto));
        return produto;
    }
    
    public Produto editar(Produto produto) throws ObjectNotFoundException {
    	Produto produtoEntity = dao.findById(produto.getIdProduto());
    	if (produtoEntity != null) {
    		produtoEntity.setNome(produto.getNome());
    		produtoEntity.setPreco(produto.getPreco());
    		return produtoEntity;
    	} else {
    		throw new ObjectNotFoundException("Produto não encontrado");
    	}
    }

    public void excluir(Long id) throws DataIntegrityException {
    	if (itensComandaService.isProdutoEmAlgumaComanda(id)) {
    		throw new DataIntegrityException("O Produto ja está sendo usado em alguma comanda");
    	}
        dao.deleteById(id);
    }

	public Produto fromDTO(ProdutoDTO produtoDTO) throws ObjectNotFoundException, AuthorizationException {
		Produto produto = new Produto();
		produto.setIdProduto(produtoDTO.getIdProduto());
		produto.setNome(produtoDTO.getNome());
		produto.setPreco(produtoDTO.getPreco());
		produto.setUsuario(usuarioService.buscar(produtoDTO.getIdUsuario()));
		return produto;
	}
}