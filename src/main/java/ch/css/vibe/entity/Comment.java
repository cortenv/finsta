package ch.css.vibe.entity;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @Column(length = 500)
    private String text;

    private String author;

    public Comment() {}

    public Comment(ImageEntity image, String text, String author) {
        this.image = image;
        this.text = text;
        this.author = author;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ImageEntity getImage() { return image; }
    public void setImage(ImageEntity image) { this.image = image; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}
