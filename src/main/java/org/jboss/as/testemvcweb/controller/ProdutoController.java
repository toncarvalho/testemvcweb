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

    @Inject
    private FacesContext facesContext;

    @Inject
    private ProdutoRepository produtoRepository;

    private List<Produto> produtoList;


    @Produces
    @Named(value = "produto")
    private Produto produto;


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
        this.produto= new Produto();

        produtoList = produtoRepository.findAllOrderedByName();
    }

    public String novo() {

        produto = new Produto();
        produto.setCodigo("000");
        produto.setDescricao("informa e nova descrição");

        System.out.println("Inicializando tela de cadastro com bean: " + produto);

        return "cadastrodeproduto";

    }

    public String salvar() {

        if (produto == null) {
            System.out.println(" o produto esta nulo!!!");
            produto = new Produto();
        }

        System.out.println(" deve persistir o produto:" + produto);


        Long id = produtoRepository.save(produto);

        System.out.println("gravou o novo produto com o id:" + id);

        produtoList.add(produto);

        return "pesquisaproduto";
    }

    private String excluir(){
        System.out.println("deve excluir") ;
        produtoRepository.delete(this.produto.getId());

        return "pesquisaproduto";
    }

    public String cancelar() {

        return "pesquisaproduto";
    }

    public String alterar() {

        return "cadastrodeproduto";
    }


    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}

