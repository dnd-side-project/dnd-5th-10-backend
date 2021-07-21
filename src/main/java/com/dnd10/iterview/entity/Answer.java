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
public class Answer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  @ManyToOne(fetch = FetchType.LAZY)
  private Question questionManager;

  @ManyToOne(fetch = FetchType.LAZY)
  private User userManager;

  // answer는 일단 수정 불가능?

  public void updateDate(LocalDate new_date){
    this.update_date = new_date;
  }

  public void likeUp(){
    this.liked++;
  }

  public void likeDown(){
    this.liked--;
  }

}
