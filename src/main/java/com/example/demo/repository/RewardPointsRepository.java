package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.RewardPoints;

public interface RewardPointsRepository extends JpaRepository<RewardPoints, Long>
{
	
}
