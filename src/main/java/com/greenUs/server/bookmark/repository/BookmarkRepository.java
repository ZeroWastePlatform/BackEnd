package com.greenUs.server.bookmark.repository;

import com.greenUs.server.bookmark.domain.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("select b from Bookmark b \n"
        + "where b.member.id = :id")
    Page<Bookmark> findByMemberId(Long id, Pageable pageable);
}
