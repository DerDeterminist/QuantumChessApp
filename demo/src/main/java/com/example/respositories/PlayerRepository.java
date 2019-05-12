package com.example.respositories;

import com.example.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player , Integer>
{
}
