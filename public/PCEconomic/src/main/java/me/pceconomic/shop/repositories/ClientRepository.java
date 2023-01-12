package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.entities.persona.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
