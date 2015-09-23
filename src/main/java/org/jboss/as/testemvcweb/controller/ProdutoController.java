package org.jboss.as.testemvcweb.controller;

import org.jboss.as.testemvcweb.data.ProdutoRepository;
import org.jboss.as.testemvcweb.model.Produto;
import org.jboss.as.testemvcweb.util.IErrorMessageUtil;
import org.jboss.as.testemvcweb.util.StatusScreen;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
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

    @Inject
    private EntityManager entityManager;


    private List<Produto> produtoList;

    private String searchKey ="";


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
        produto.setFabricante("entre com o fabricante do produto");
        produto.setDescricao("entre com o nome do produto");

        System.out.println("Inicializando tela de cadastro com bean: " + produto);

        this.statusScreen = StatusScreen.INSERINDO;

        return "cadastrodeproduto";

    }

    public String salvar() throws Exception {

        try {


            if (this.statusScreen.equals(StatusScreen.INSERINDO)) {

                Long id = produtoRepository.save(produto);

                System.out.println("gravou o novo produto com o id:" + id);

                produtoList.add(produto);

            } else if (this.statusScreen.equals(StatusScreen.EDITANDO)) {
                System.out.println(" o produto é: " + produto + " e o estado é :" + this.statusScreen);
                produtoRepository.update(produto.getId(), produto);
            }

            this.statusScreen = StatusScreen.PESQUISANDO;
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Falha ao Salvar registro!!!");
            facesContext.addMessage(null, m);
        }
        return "pesquisaproduto";
    }

    public String excluir() throws Exception {
        try {
            produtoList.remove(produto);
            produtoRepository.delete(this.produto.getId());

        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Falha ao excluir registro!!!");
            facesContext.addMessage(null, m);
        }

        return "pesquisaproduto";
    }

    public String cancelar() {

        return "pesquisaproduto";
    }

    public String alterar() throws Exception {
        this.statusScreen = StatusScreen.EDITANDO;

        return "cadastrodeproduto";
    }


    public void pesquisaGenerica(ActionEvent event) {

        System.out.println(" deve executar a pesquisa generica com a chave:" + searchKey);

        produtoList = produtoRepository.pesquisaGenerica(searchKey);
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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}

