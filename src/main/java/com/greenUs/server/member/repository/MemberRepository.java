package com.greenUs.server.member.repository;

import com.greenUs.server.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);
    Member findByEmail(String email);
}
