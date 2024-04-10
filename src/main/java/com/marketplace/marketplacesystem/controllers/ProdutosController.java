package com.marketplace.marketplacesystem.controllers;

import java.io.InputStream;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.marketplace.marketplacesystem.models.Produto;
import com.marketplace.marketplacesystem.models.ProdutoDto;
import com.marketplace.marketplacesystem.services.ProdutosRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {
	
	// Campo para fazer a requisição do repositório:
	@Autowired
	private ProdutosRepository repo;
	
	// O método que permite ler os produtos do banco de dados:
	@GetMapping({"", "/"})
	public String mostrarListaDeProdutos (Model model) {
	List<Produto> produtos = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));	
	model.addAttribute("produtos", produtos);
	return "produtos/index";
	}	
	
	
	@GetMapping("/criar")
	public String showCreatePage(Model model) {
		ProdutoDto produtoDto = new ProdutoDto();
		model.addAttribute("produtoDto", produtoDto);
		return "produtos/criar";
	}

	@PostMapping("/criar")
	public String createProduct(
			@Valid @ModelAttribute ProdutoDto produtoDto,
			BindingResult result
			) {
		
		if (produtoDto.getNomeDaImagem().isEmpty()) {
			result.addError(new FieldError("productDto", "nomeDaImagem", "A imagem é necessária"));
		}
		
		if(result.hasErrors()) {
			return "produtos/criar";
		}
		
		
		// Salvar o arquivo da imagem
		MultipartFile image = produtoDto.getNomeDaImagem();
		Date criadoNo = new Date(System.currentTimeMillis());
		String salvarNomeImagem = criadoNo.getTime() + "_" + image.getOriginalFilename();

		
		try {
			String uploadDir = "public/imagens/";
			Path uploadPath = Paths.get(uploadDir);
			
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
		}
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + salvarNomeImagem), 
						StandardCopyOption.REPLACE_EXISTING);
			}
		}catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
			}
		
		Produto produto = new Produto();
		produto.setNome(produtoDto.getNome());
		produto.setMarca(produtoDto.getMarca());
		produto.setCategoria(produtoDto.getCategoria());
		produto.setPreco(produtoDto.getPreco());
		produto.setDescricao(produtoDto.getDescricao());
		produto.setCriadoNo(criadoNo);
		produto.setNomeDaImagem(salvarNomeImagem);
		
		repo.save(produto);
		
		return "redirect:/produtos";
	}
	
	@GetMapping("/editar")
	public String showEditPage(
			Model model,
			@RequestParam int id
			) {
		
		try {
			Produto produto = repo.findById(id).get();
			model.addAttribute("produto", produto);
			
			
			ProdutoDto produtoDto = new ProdutoDto();
			produtoDto.setNome(produto.getNome());
			produtoDto.setMarca(produto.getMarca());
			produtoDto.setCategoria(produto.getCategoria());
			produtoDto.setPreco(produto.getPreco());
			produtoDto.setDescricao(produto.getDescricao());
			
			model.addAttribute("produtoDto", produtoDto);
		}
		catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			return "redirect:/produtos";
		}
		return "produtos/editar";
	}
	
	@PostMapping("/editar")
		public String atualizarProduto(
				Model model,
				@RequestParam int id,
				@Valid @ModelAttribute ProdutoDto produtoDto,
				BindingResult result
				) {
		
		try {
			Produto produto = repo.findById(id).get();
			model.addAttribute("produto", produto);
			
			if (result.hasErrors()) {
				return "produtos/editar";
				}
				
			if (!produtoDto.getNomeDaImagem().isEmpty()) {
				// Deletar a imagem antiga:
				String uploadDir = "public/imagens/";
				Path imagemAntigaPath = Paths.get(uploadDir + produto.getNomeDaImagem());
			
			try {
				Files.delete(imagemAntigaPath);
			}			
			catch(Exception ex){
				System.out.println("Exception: " + ex.getMessage());
			}
			MultipartFile imagem = produtoDto.getNomeDaImagem();
			Date criadoNo = new Date(System.currentTimeMillis());
			String armazNomeImagem = criadoNo.getTime() + "_" + imagem.getOriginalFilename();
			
			try (InputStream inputStream = imagem.getInputStream()){
				Files.copy(inputStream, Paths.get(uploadDir + armazNomeImagem),
						StandardCopyOption.REPLACE_EXISTING);
			}
			catch(Exception ex){
				System.out.println("Exception: " + ex.getMessage());
			}
			
			produto.setNomeDaImagem(armazNomeImagem);	
			}
			
			produto.setNome(produtoDto.getNome());
			produto.setMarca(produtoDto.getMarca());
			produto.setCategoria(produtoDto.getCategoria());
			produto.setPreco(produtoDto.getPreco());
			produto.setDescricao(produtoDto.getDescricao());
			
			repo.save(produto);
		}
		
		catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		return "redirect:/produtos";
		}
	
	
	
		@GetMapping("/deletar")
		public String deletarProduto(
				@RequestParam int id
				) {
		
		try {
			Produto produto = repo.findById(id).get();
			
			Path pathImagem = Paths.get("public/imagens/" + produto.getNomeDaImagem());
			
			try {
				Files.delete(pathImagem);
			}
			catch (Exception ex){
				System.out.println("Exception: " + ex.getMessage());
			}
		
			repo.delete(produto);
		}
		catch (Exception ex){
			System.out.println("Exception: " + ex.getMessage());
		}
		
		
		return "redirect:/produtos";	
		}
}