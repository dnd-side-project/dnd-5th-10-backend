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
public class BookmarkQuestion {
  @Id
  @GeneratedValue
  @Column(name = "bookmark_question_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Bookmark bookmarkManager;

  @ManyToOne(fetch = FetchType.LAZY)
  private Question questionManager;
}
