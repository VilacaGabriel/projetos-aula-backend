package com.gabriel.cadastro_livros.repository;

import com.gabriel.cadastro_livros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    // Métodos personalizados para busca
    List<Livro> findByTituloContaining(String titulo);
    List<Livro> findByAutorContaining(String autor);
}
