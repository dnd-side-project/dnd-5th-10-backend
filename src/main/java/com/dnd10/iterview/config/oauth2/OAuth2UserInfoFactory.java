package com.dnd10.iterview.config.oauth2;

import com.dnd10.iterview.config.oauth2.GoogleOAuth2Info;
import com.dnd10.iterview.config.oauth2.OAuth2UserInfo;
import com.dnd10.iterview.entity.AuthProvider;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2Info(attributes);
        } else {
            OAuth2Error oauth2Error = new OAuth2Error("oauth2_not_found",
                    "Not supported oauth2 client", null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }
    }
}
