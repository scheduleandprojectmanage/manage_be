package com.project.scheduling.user.repository;

import com.project.scheduling.user.entity.ProviderInfo;
import com.project.scheduling.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //entity를 통해 db에 접근해서 데이터를 담아와서 집어넣는 대리인 역할

    //이메일 중복확인
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.identifier = :identifier AND u.providerInfo = :providerInfo")
    Optional<User> findByOAuthInfo(String identifier, ProviderInfo providerInfo);
    Optional<User> findByIdentifier(String identifier);
}
