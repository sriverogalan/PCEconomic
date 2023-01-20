package me.pceconomic.shop.repositories;

import me.pceconomic.shop.domain.article.propietats.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
