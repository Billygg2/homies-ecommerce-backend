package com.yavirac.homies.catalogo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yavirac.homies.productos.ProductoRepository;

import jakarta.transaction.Transactional;


@Service
public class CatalogoService {
    @Autowired
    private CatalogoRepository catalogoRepository;

        @Autowired
    private ProductoRepository productoRepository;

    public List<CatalogoEntity> getAllCatalogos() {
        return catalogoRepository.findAll();
    }

    @Transactional
    public CatalogoEntity crearCatalogo(CatalogoEntity catalogo) {
        if (catalogo.getNombreCatalogo() == null) {
            throw new IllegalArgumentException("El nombre del catalogo no puede estar vacío");
        }
        return catalogoRepository.save(catalogo);
    }

    public CatalogoEntity getCatalogoById(Long id_catalogo) {
        return catalogoRepository.findById(id_catalogo).orElseThrow(() -> new RuntimeException("Catálogo no encontrado"));
    }

    
    @Transactional
    public void deleteCatalogo(Long id_catalogo) {
        CatalogoEntity catalogo = catalogoRepository.findById(id_catalogo)
                .orElseThrow(() -> new RuntimeException("Catálogo no encontrado"));
    
        long productCount = productoRepository.countByCatalogo(catalogo);
        
        if (productCount > 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "No se puede eliminar el catálogo porque tiene productos asociados"
            );
        }
    
        catalogoRepository.delete(catalogo);
    }

    public CatalogoEntity updateCatalogo(Long id_catalogo, CatalogoEntity catalogoDetails) {
        CatalogoEntity catalogo = catalogoRepository.findById(id_catalogo)
                .orElseThrow(() -> new RuntimeException("Catálogo no encontrado"));

        catalogo.setNombreCatalogo(catalogoDetails.getNombreCatalogo());
        
        return catalogoRepository.save(catalogo);
    }
    
}