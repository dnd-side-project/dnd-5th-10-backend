package com.dnd10.iterview.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRequestDto {
  @NotBlank
  private String name; // todo: name이 조금 북마크 제목을 나타내기에 애매한거 같아서 title로 변경 생각중.
  // 나중에 db 컬럼명 바꿀수 있음. 일단은 프론트가 수월히 테스트해야 하니 그대로 유지

}
