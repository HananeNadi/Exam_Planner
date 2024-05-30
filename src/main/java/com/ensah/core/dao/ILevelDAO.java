package com.ensah.core.dao;

import com.ensah.core.bo.Group;
import com.ensah.core.bo.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILevelDAO extends JpaRepository<Level, Long> {
}
