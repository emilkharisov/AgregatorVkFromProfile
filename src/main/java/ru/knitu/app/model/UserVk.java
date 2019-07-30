package ru.knitu.app.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Table(name = "users_vk")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVk  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer userId;
    private String accessToken;

    public UserVk(Integer userId, String accessToken, String name) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.name = name;
    }
}
