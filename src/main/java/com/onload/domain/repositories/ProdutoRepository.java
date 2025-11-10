package com.onload.domain.repositories;

import com.onload.domain.models.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> listByFornecedor(Long fornecedorId) {
        return find("fornecedor.id", fornecedorId).list();
    }
}
