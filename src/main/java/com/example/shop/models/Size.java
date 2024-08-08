package com.example.shop.models;

import com.example.shop.models.enums.SizeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Table(name = "sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Long id;
    private Integer numberSize;
    private String textSize;
    private SizeType sizeType;

    public Size(long l) {
        this.id = l;
    }
}
