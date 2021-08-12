package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Bookmark;
import com.dnd10.iterview.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
  Page<Bookmark> findAllByUserManager(User user, Pageable pageable);
}
