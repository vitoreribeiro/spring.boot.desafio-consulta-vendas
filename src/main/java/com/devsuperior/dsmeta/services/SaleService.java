package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SumSellerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name, Pageable pageable) {

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate today = LocalDate.ofInstant(Instant.now(),ZoneId.systemDefault());

		LocalDate endDate;
		if (maxDate.isBlank())
			endDate = today;
		else
			endDate = LocalDate.parse(maxDate, formatter);

		LocalDate startDate;
		if (minDate.isBlank())
			startDate = endDate.minusYears(1L);
		 else
			startDate = LocalDate.parse(minDate, formatter);

		return repository.findReport(startDate, endDate, name, pageable);
	}

	public Page<SumSellerDTO> getSummary(String minDate, String maxDate, Pageable pageable){

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		LocalDate today = LocalDate.ofInstant(Instant.now(),ZoneId.systemDefault());

		LocalDate endDate;
		if (maxDate.isBlank())
			endDate = today;
		else
			endDate = LocalDate.parse(maxDate, formatter);

		LocalDate startDate;
		if (minDate.isBlank())
			startDate = endDate.minusYears(1L);
		else
			startDate = LocalDate.parse(minDate, formatter);

		return repository.findSummary(startDate, endDate, pageable);
	}
}
