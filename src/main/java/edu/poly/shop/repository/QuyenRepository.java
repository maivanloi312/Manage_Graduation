package edu.poly.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Quyen;

@Repository
public interface QuyenRepository extends JpaRepository<Quyen, Long>{
}
