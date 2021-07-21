package com.dnd10.iterview.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "question_id")
  private Long id;

  // title x

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate create_date;

  @Column(nullable = false)
  private Long bookmark_count;

  @ManyToOne(fetch = FetchType.LAZY)
  private User userManager;
}
