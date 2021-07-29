package com.dnd10.iterview.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.dnd10.iterview.controller.QuestionController;
import com.dnd10.iterview.dto.QuestionResponseDto;
import org.springframework.hateoas.EntityModel;

public class QuestionResource extends EntityModel<QuestionResponseDto> {

  public static EntityModel<QuestionResponseDto> modelOf(QuestionResponseDto questionDto){
    EntityModel<QuestionResponseDto> questionResource = EntityModel.of(questionDto);
    questionResource.add(linkTo(QuestionController.class).withSelfRel());

    return questionResource;
  }

}
