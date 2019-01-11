package fmi.usm.md.mvc.controller;

import fmi.usm.md.mvc.model.Group;
import fmi.usm.md.mvc.model.User;
import fmi.usm.md.mvc.model.UserJson;
import fmi.usm.md.mvc.service.GroupService;
import fmi.usm.md.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserRestController {

    private final UserService userService;
    private final GroupService groupService;

    @RequestMapping(value = "/login", method = POST)
    public LinkedHashMap<String, Object> loginRest(
                                                    @RequestParam(name = "username") String username,
                                                    @RequestParam(name = "password") String password) {

        String hash = "$2a$10$mL0Xwpe8NThYuToTCepO3u";
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Optional<User> uo = userService.getUserByUsername(username);
        User user = new User();
        if (uo.isPresent()) {
            user = uo.get();
        }

        String hashPassword = BCrypt.hashpw(password, hash);

        if(!user.getPassword().equals(hashPassword) || username == null) {
            map.put("status", "fail");
            map.put("message", "Credentials are incorrect");
            return map;
        }

        map.put("status", "success");
        UserJson userJson = new UserJson(user);
        map.put("user", userJson);
        return map;
    }

    @RequestMapping(value = "/register", method = POST)
    public LinkedHashMap<String, Object> registerRest(
                                                        @RequestParam(value = "username") String username,
                                                        @RequestParam(value = "mail") String mail,
                                                        @RequestParam(value = "password") String password,
                                                        @RequestParam(value = "groupName") String groupName) {

        String hash = "$2a$10$mL0Xwpe8NThYuToTCepO3u";
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        User user = new User();
        Group g;

        if (groupName != null) {
            Optional<Group> go = groupService.getGroupByName(groupName);

            if (go.isPresent()) {
                g = go.get();
            }
            else {
                map.put("status", "fail");
                map.put("message", "Specified groupName doesnt exist!");
                return map;
            }
            user.setGroup(g);
        }
        else {
            map.put("status", "fail");
            map.put("message", "groupName is null!");
            return map;
        }


        Optional<User> tmpO = userService.getUserByUsername(username);

        if (tmpO.isPresent()) {
            map.put("status", "fail");
            map.put("message", "User with username '"+ username + "' already exists!");
            return map;
        }

        user.setUsername(username);
        user.setMail(mail);
        user.setPassword(BCrypt.hashpw(password, hash));

        map.put("status", "success");
        userService.add(user);

        return map;
    }
}
