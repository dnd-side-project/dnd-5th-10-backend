package com.dnd10.iterview.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
  @Id
  @GeneratedValue
  @Column(name = "answer_id")
  private Long id;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate create_date;

  @Column(nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate update_date;

  @Column(nullable = false)
  private Long liked;

  @Column(nullable = false)
  private Long bookmark_count; // answer가 아니라 question에 있어야 할 것 같음

  @ManyToOne(fetch = FetchType.LAZY)
  private Question questionManager;

  @ManyToOne(fetch = FetchType.LAZY)
  private User userManager;

}
