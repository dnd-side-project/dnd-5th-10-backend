package com.dnd10.iterview.entity;

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

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class LikeAnswer {
  @Id
  @GeneratedValue
  @Column(name = "like_answer_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User userManager;

  @ManyToOne(fetch = FetchType.LAZY)
  private Answer answerManager;
}
