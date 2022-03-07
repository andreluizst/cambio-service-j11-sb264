package br.com.alst.softwares.cambioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alst.softwares.cambioservice.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long>{

	Cambio findByFromAndTo(String from, String to);
}
