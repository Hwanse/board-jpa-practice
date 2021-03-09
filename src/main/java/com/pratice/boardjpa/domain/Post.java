package com.pratice.boardjpa.domain;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String name;

    @Lob
    private String contents;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifyDate;

    private Boolean useFlag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Post parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Post> child = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public static Post createPost(String name, String contents, Boolean useFlag, Board board) {
        Post post = new Post();
        post.name = name;
        post.contents = contents;
        post.useFlag = useFlag;
        post.board = board;
        return post;
    }

    public static Post createReplyPost(String name, String contents, Boolean useFlag, Board board,
                                       Post parent) {
        Post post = new Post();
        post.name = name;
        post.contents = contents;
        post.useFlag = useFlag;
        post.board = board;
        post.parent = parent;
        parent.getChild()
              .add(post);

        return post;
    }


    public void modifyPost(Post post) {
        this.name = post.getName();
        this.contents = post.getContents();
        this.useFlag = post.getUseFlag();
    }

    public void deleteChilds() {
        this.child.clear();
        this.comments.clear();
    }

}
