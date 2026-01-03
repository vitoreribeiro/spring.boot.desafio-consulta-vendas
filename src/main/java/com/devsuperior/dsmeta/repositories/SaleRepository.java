package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SumSellerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query( "SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(s.id,s.date,s.amount,se.name) \n" +
            "  FROM Sale s                                                                     \n" +
            "  JOIN s.seller se                                                                \n" +
            " WHERE s.date BETWEEN :startDate AND :endDate                                     \n" +
            "   AND UPPER(se.name) LIKE CONCAT('%', UPPER(:sellerName), '%')                   \n" )
    Page<SaleReportDTO> findReport( @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate,
                                    @Param("sellerName") String sellerName,
                                    Pageable pageable);

    @Query( "SELECT new com.devsuperior.dsmeta.dto.SumSellerDTO( se.name, SUM(s.amount)) \n" +
            "  FROM Sale s                                                               \n" +
            "  JOIN s.seller se                                                          \n" +
            " WHERE s.date BETWEEN :startDate AND :endDate                               \n" +
            " GROUP BY se.name                                                           \n" )
    Page<SumSellerDTO> findSummary( @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate,
                                    Pageable pageable);

}
