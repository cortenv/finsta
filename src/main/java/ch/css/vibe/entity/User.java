package ch.css.vibe.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String password;

    private byte[] profileImage;
    private String profileImageType;

    // Getters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public byte[] getProfileImage() { return profileImage; }
    public String getProfileImageType() { return profileImageType; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setProfileImage(byte[] profileImage) { this.profileImage = profileImage; }
    public void setProfileImageType(String profileImageType) { this.profileImageType = profileImageType; }
}
