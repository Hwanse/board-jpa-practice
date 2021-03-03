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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifyDate;

    private String contents;

    private Character useFlag;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(fetch = LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> child = new ArrayList<>();

    public static Comment createComment(Post post, String contents, Character useFlag) {
        Comment comment = new Comment();
        comment.post = post;
        comment.contents = contents;
        comment.useFlag = useFlag;
        return comment;
    }

    public static Comment createReplyComment(Post post, Comment parent, String contents, Character useFlag) {
        Comment comment = new Comment();
        comment.post = post;
        comment.contents = contents;
        comment.useFlag = useFlag;
        comment.parent = parent;
        parent.getChild().add(comment);
        return comment;
    }

    public void deleteChild() {
        this.child.clear();
    }

}
