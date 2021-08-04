package com.dnd10.iterview.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate create_date;

  @Column(nullable = false)
  private Long bookmarkCount; // _는 jpa에서 property를 찾는 경로 예약어이므로, 카멜케이스로 생성할 것.

  @ManyToOne(fetch = FetchType.LAZY)
  private User userManager;

  // question tag 양방향 매핑
  @OneToMany(mappedBy = "question")
  private List<QuestionTag> questionTagList = new ArrayList<>();

  public void likeUp(){
    this.bookmarkCount++;
  }

  public void likeDown(){
    this.bookmarkCount--;
  }

  public void addTag(QuestionTag tag){
    questionTagList.add(tag);
  }
}
