package com.marketplace.marketplacesystem.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

public class ProdutoDto {
    @NotBlank(message = "O campo nome é necessário")
    private String nome;
    
    @NotBlank(message = "O campo marca é necessário")
    private String marca;
    
    @NotBlank(message = "O campo categoria é necessário")
    private String categoria;
    
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero")
    private double preco;
    
    @Size(min = 10, message = "A descrição deve conter no mínimo 10 caracteres")
    @Size(max = 2000, message = "A descrição deve conter no máximo 2000 caracteres")
    private String descricao;
    
    private MultipartFile nomeDaImagem;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MultipartFile getNomeDaImagem() {
		return nomeDaImagem;
	}

	public void setNomeDaImagem(MultipartFile nomeDaImagem) {
		this.nomeDaImagem = nomeDaImagem;
	}
}