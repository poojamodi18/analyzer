package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.dao.UserRepository;
import com.polyrepo.analyzer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHomeService{
    private UserRepository userRepository;

    @Autowired
    public UserHomeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save (User user){
       return userRepository.save(user);
    }

    public User getUser (String login){
        List<User> users = userRepository.findByLogin(login);
        if(users.size()>0){
            return userRepository.findByLogin(login).get(0);
        }else{
            return null;
        }

    }

    public User getUserDetails(String username, String name, String login, String avatarUrl, String url) {
        User userObj = getUser(login);
        if(userObj==null){
            userObj = new User(username,login,avatarUrl,url);
            save(userObj);
        }
        else{
            userObj.setName(username);
            userObj.setLogin(login);
            userObj.setAvatarUrl(avatarUrl);
            userObj.setUrl(url);
            save(userObj);
        }
        return userObj;
    }
}
