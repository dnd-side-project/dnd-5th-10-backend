package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.QuestionTag;
import com.dnd10.iterview.entity.Tag;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface QuestionSearchExtension {

  List<Question> findWithTags(List<String> tagList, String sort);
  //List<Question> findWithTagsRandom(List<String> tagList);
}
