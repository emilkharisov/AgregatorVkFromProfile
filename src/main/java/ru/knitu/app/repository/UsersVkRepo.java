package ru.knitu.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.knitu.app.model.UserVk;


public interface UsersVkRepo extends JpaRepository<UserVk, Integer> {
    UserVk findUserVkById(int id);
    UserVk findFirstByUserId(int id);
}
