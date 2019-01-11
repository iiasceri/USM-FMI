package fmi.usm.md.mvc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class UserJson {
    private String username;
    private String mail;
    private String password;
    private String groupName;

    public UserJson(User user) {
        username = user.getUsername();
        mail = user.getUsername();
        password = user.getPassword();
        groupName = user.getGroup().getName();
    }
}
