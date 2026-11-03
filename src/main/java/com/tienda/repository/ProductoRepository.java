package com.tienda.repository;

import com.tienda.domain.Producto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    public List<Producto> findByActivoTrue();

    //Consulta derivada
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(BigDecimal precioInf, BigDecimal precioSup);

    //Consulta JPQL
    @Query(value = "SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(BigDecimal precioInf, BigDecimal precioSup);

    //Consulta SQL 
    @Query(nativeQuery = true,
            value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(BigDecimal precioInf, BigDecimal precioSup);
    
        // Consulta ampliada JPQL productos por rango de precio, categoría opcional y disponibilidad.
    @Query("SELECT p FROM Producto p JOIN p.categoria c "
            + "WHERE p.precio BETWEEN :precioInf AND :precioSup "
            + "AND p.existencias > 0 "
            + "AND p.activo = true "
            + "AND c.activo = true "
            + "AND (:idCategoria IS NULL OR c.idCategoria = :idCategoria)")
    public List<Producto> consultaAmpliada(
            BigDecimal precioInf,
            BigDecimal precioSup,
            Integer idCategoria);
}
