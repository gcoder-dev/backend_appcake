package com.example.demo.repositories;


import com.example.demo.entities.Pastel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PastelRepository extends JpaRepository<Pastel, Long> {
}