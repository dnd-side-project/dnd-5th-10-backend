package com.dnd10.iterview.config.oauth2;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OAuthProperties {

  @Value("${oauth.redirect.url}")
  private String redirectUrl;

}
