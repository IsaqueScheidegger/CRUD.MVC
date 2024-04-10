package com.marketplace.marketplacesystem.models;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nome;
	private String marca;
	private String categoria;
	private double preco;
	
	@Column(columnDefinition = "TEXT")
	private String descricao;
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	private Date criadoNo;
	private String nomeDaImagem;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getCriadoNo() {
		return criadoNo;
	}
	public void setCriadoNo(Date criadoNo) {
		this.criadoNo = criadoNo;
	}
	public String getNomeDaImagem() {
		return nomeDaImagem;
	}
	public void setNomeDaImagem(String nomeDaImagem) {
		this.nomeDaImagem = nomeDaImagem;
	}
	
	
}
