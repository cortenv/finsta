package ch.css.vibe.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String contentType;

    @Column(length = 1000)
    private String description;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    // Transient field for comments to use in Thymeleaf
    @Transient
    private List<Comment> comments = new ArrayList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}
