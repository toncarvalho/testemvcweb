package org.jboss.as.testemvcweb.controller;

import org.jboss.as.testemvcweb.data.ProdutoRepository;
import org.jboss.as.testemvcweb.model.Produto;
import org.jboss.as.testemvcweb.util.IErrorMessageUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named(value = "produtoController")
@SessionScoped
public class ProdutoController implements IErrorMessageUtil, Serializable {

    private StatusScreen statusScreen = StatusScreen.PESQUISANDO;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoRepository produtoRepository;

    private List<Produto> produtoList;


    @Produces
    @Named(value = "produto")
    private Produto produto;

    private Produto produtoSelecionado;


    @Produces
    @Named
    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }


    public void onProdutosListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Produto produto) {
        retrieveAllProdutosOrderedByName();
    }

    @PostConstruct
    public void retrieveAllProdutosOrderedByName() {
        this.produto = new Produto();

        produtoList = produtoRepository.findAllOrderedByName();
    }

    public String novo() {

        produto = new Produto();
        produto.setCodigo("000");
        produto.setDescricao("informa e nova descrição");

        System.out.println("Inicializando tela de cadastro com bean: " + produto);

        this.statusScreen = StatusScreen.INSERINDO;

        return "cadastrodeproduto";

    }

    public String salvar() {

        System.out.println(" deve persistir o produto:" + produto);

        if (this.statusScreen.equals(StatusScreen.INSERINDO)) {

            Long id = produtoRepository.save(produto);

            System.out.println("gravou o novo produto com o id:" + id);

            produtoList.add(produto);

        } else if (this.statusScreen.equals(StatusScreen.EDITANDO)) {
            System.out.println(" o produto é: " + produto + " e o estado é :" + this.statusScreen);
            produtoRepository.update(produto.getId(), produto);
        }

        this.statusScreen = StatusScreen.PESQUISANDO;
        return "pesquisaproduto";
    }

    public String excluir() {
        System.out.println("deve excluir");
        produtoRepository.delete(this.produto.getId());

        return "pesquisaproduto";
    }

    public String cancelar() {

        return "pesquisaproduto";
    }

    public String alterar() {
        this.statusScreen = StatusScreen.EDITANDO;

        return "cadastrodeproduto";
    }


    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }
}

