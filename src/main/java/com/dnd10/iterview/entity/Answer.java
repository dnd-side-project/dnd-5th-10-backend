package com.dnd10.iterview.entity;

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

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long id;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Long liked;

  @ManyToOne(fetch = FetchType.LAZY)
  private Question question;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  // answer는 일단 수정 불가능?

  public void likeUp(){
    this.liked++;
  }

  public void likeDown(){
    this.liked--;
  }


}
