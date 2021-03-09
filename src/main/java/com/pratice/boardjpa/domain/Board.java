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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = LAZY, mappedBy = "board", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "use_flag")
    private Boolean useFlag;

    public static Board createBoard(String name, Boolean useFlag) {
        Board board = new Board();
        board.name = name;
        board.useFlag = useFlag;
        return board;
    }

    public void deletePosts() {
        posts.clear();
    }


}
