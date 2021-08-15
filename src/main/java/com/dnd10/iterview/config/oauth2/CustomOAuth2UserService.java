package com.dnd10.iterview.config.oauth2;

import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            OAuth2Error oauth2Error = new OAuth2Error("email_not_found",
                    "Email not found from OAuth2 provider", null);
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        Optional<User> userOptional = userRepository.findUserByEmail(oAuth2UserInfo.getEmail());
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                OAuth2Error oauth2Error = new OAuth2Error("wrong_account_provider",
                        "login with wrong provider email", null);
                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(),
                oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Object imageUrl;
        if (oAuth2UserInfo instanceof GithubOAuth2Info) {
            imageUrl = oAuth2UserInfo.getAttributes().get("avatar_url");
        } else {
            imageUrl = oAuth2UserInfo.getAttributes().get("picture");
        }

        User user = User.builder()
            .email(oAuth2UserInfo.getEmail())
            .username(oAuth2UserInfo.getName())
            .imageUrl(String.valueOf(imageUrl))
            .provider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
            .providerId(oAuth2UserInfo.getId())
            .build();

        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.update(oAuth2UserInfo.getName());
        // return userRepository.save(existingUser); ?? update인데 save가 왜있지?
        return existingUser;
    }

}
