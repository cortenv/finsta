package ch.css.vibe.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "images")
public class ImageEntity {

    @Id
    private String id;  // MongoDB ID as String

    private String description;
    private byte[] data;
    private String fileName;
    private String contentType;

    private String userId;   // MongoDB user ID
    private String username;

    private List<Comment> comments = new ArrayList<>();

    private byte[] userProfileImage;
    private String userProfileImageType;

    // --- Getters / Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }

    public byte[] getUserProfileImage() { return userProfileImage; }
    public void setUserProfileImage(byte[] userProfileImage) { this.userProfileImage = userProfileImage; }

    public String getUserProfileImageType() { return userProfileImageType; }
    public void setUserProfileImageType(String userProfileImageType) { this.userProfileImageType = userProfileImageType; }
}
