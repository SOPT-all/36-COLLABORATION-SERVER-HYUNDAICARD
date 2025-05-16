package org.soptcollab.web1.hyundaicard.domain.card;

import jakarta.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

  @Query(
      "select card as cardResult, count(card_tag.tag) as matched_tag_count"
          + " from Card card"
          + "         left join card.cardTags card_tag on card_tag.tag.id in :tagIds"
          + "         join card.image image"
          + " where image.height > 200"
          + " group by card"
          + " order by matched_tag_count desc"
          + " limit 1"
  )
  Optional<Tuple> getVerticalCardOrderByTags(@Param("tagIds") List<String> tagIds);

  @Query(
      "select card as cardResult, count(card_tag.tag) as matched_tag_count"
          + " from Card card"
          + "         left join card.cardTags card_tag on card_tag.tag.id in :tagIds"
          + "         join card.image image"
          + " where image.height <= 200"
          + " group by card"
          + " order by matched_tag_count desc"
  )
  List<Tuple> getHorizontalCardOrderByTags(List<String> tagIds, Pageable pageable);
}