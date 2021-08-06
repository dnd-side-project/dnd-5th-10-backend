package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Bookmark;
import com.dnd10.iterview.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
  boolean existsByNameAndUserManager(String name, User user);
  List<Bookmark> findAllByUserManager(User user);
}
