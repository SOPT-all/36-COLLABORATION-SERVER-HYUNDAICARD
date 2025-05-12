package org.soptcollab.web1.hyundaicard.repository;

import org.soptcollab.web1.hyundaicard.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

}