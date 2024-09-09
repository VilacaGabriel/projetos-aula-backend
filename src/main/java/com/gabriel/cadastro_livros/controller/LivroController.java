package com.gabriel.cadastro_livros.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.cadastro_livros.model.Livro;
import com.gabriel.cadastro_livros.repository.LivroRepository;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    // Listar todos os livros
    @GetMapping
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    // Buscar livros por título
    @GetMapping("/titulo/{titulo}")
    public List<Livro> buscarPorTitulo(@PathVariable String titulo) {
        return livroRepository.findByTituloContaining(titulo);
    }

    // Buscar livros por autor
    @GetMapping("/autor/{autor}")
    public List<Livro> buscarPorAutor(@PathVariable String autor) {
        return livroRepository.findByAutorContaining(autor);
    }

    // Cadastrar novo livro
    @PostMapping
    public Livro cadastrarLivro(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }

    // Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            return ResponseEntity.ok(livro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar livro
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            Livro livroExistente = livro.get();
            livroExistente.setTitulo(livroAtualizado.getTitulo());
            livroExistente.setAutor(livroAtualizado.getAutor());
            livroExistente.setIsbn(livroAtualizado.getIsbn());
            livroExistente.setDataPublicacao(livroAtualizado.getDataPublicacao());
            return ResponseEntity.ok(livroRepository.save(livroExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
