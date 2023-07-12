package com.example.worly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.worly.user.User;
import com.example.worly.user.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
