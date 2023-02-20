package com.greenUs.server.member.repository;

import com.greenUs.server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
    Member findByEmail(String email);

    @Query("select m from Member m left join fetch m.baskets where m.id = :id")
    Optional<Member> findByIdFetchBaskets(@Param("id") Long id);
}
