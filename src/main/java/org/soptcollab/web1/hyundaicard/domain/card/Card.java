package org.soptcollab.web1.hyundaicard.domain.card;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.soptcollab.web1.hyundaicard.global.common.entity.BaseEntity;

@Entity
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;
}
