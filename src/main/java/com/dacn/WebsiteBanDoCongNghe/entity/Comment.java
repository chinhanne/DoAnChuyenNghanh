package com.dacn.WebsiteBanDoCongNghe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @ManyToOne(optional = true) // Khong bat buoc co
    @JoinColumn(name = "parent_comment_id")
    Comment parentComment;

//    orphanRemoval = true: Bảo đảm rằng khi một comment con bị loại bỏ khỏi danh sách childComments, nó sẽ bị xóa khỏi cơ sở dữ liệu.
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> childComments; // Danh sách comment con

    String content;
    boolean display;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;

    //    Khởi tạo thời gian tạo comment
    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
        this.display = true;
    }

    //    Khởi tạo thời gian update comment
    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
