package com.dnd10.iterview.config.oauth2;

import java.util.Map;

public class GithubOAuth2Info extends OAuth2UserInfo {

  public GithubOAuth2Info(Map<String, Object> attributes) {
    super(attributes);
  }

  @Override
  public String getId() {
    return Integer.toString((int)attributes.get("id"));
  }

  @Override
  public String getName() {
    return (String) attributes.get("login");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }
}
