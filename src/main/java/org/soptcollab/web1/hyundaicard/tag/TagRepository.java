package org.soptcollab.web1.hyundaicard.tag;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {

  List<Tag> findAllByIdIn(List<String> ids);
}
