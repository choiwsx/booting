//package org.kitchen.booting.controller;
//
//import org.kitchen.booting.domain.UserVO;
//import org.kitchen.booting.repository.UserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping(value = "/user")
//@CrossOrigin(origins = "chrome-extension://aejoelaoggembcahagimdiliamlcdmfm")
//public class UserController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
//
//    //@Autowired
//    //UserRepository userRepository;
//
//    @GetMapping("/")
//    public ResponseEntity<List<UserVO>> getAllUsers() {
//        try {
//            List<UserVO> users = new ArrayList<UserVO>();
//            userRepository.findAll().forEach(users::add);
//
//            if (users.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/Users")
//    public ResponseEntity<List<UserVO>> getAllUsers(@RequestParam(required = false) String nickname) {
//        try {
//            List<UserVO> users = new ArrayList<UserVO>();
//
//            if (nickname == null)
//                userRepository.findAll().forEach(users::add);
//            else
//                userRepository.findByNickname(nickname).forEach(users::add);
//
//            if (users.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            users.forEach(a-> System.out.println(a));
//
//            return new ResponseEntity<>(users, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/Users/{id}")
//    public ResponseEntity<UserVO> getUserById(@PathVariable("id") String userId) {
//        Optional<UserVO> userData = userRepository.getByUserId(userId);
//
//        if (userData.isPresent()) {
//            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
////    @PostMapping("/Users")
////    public ResponseEntity<UserVO> createUser(@RequestBody UserVO user) {
////        System.out.println(user);
////        LOGGER.info("@@@"+user.toString());
////        try {
////            UserVO _user = userRepository.save(new UserVO(user.getUserId(), user.getNickname(), user.getEmail(), user.getImg(), user.getBio(), user.getUrl()));
////            return new ResponseEntity<>(_user, HttpStatus.CREATED);
////        } catch (Exception e) {
////            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
////        }
////    }
//
////    @PutMapping("/Users/{id}")
////    public ResponseEntity<UserVO> updateUser(@PathVariable("id") String userId, @RequestBody UserVO user) {
////        Optional<UserVO> userData = userRepository.getByUserId(userId);
////
////        if (userData.isPresent()) {
////            UserVO _user = userData.get();
////            _user.setNickname(user.getNickname());
////            _user.setEmail(user.getEmail());
////            _user.setImg(user.getImg());
////            _user.setBio(user.getBio());
////            _user.setUrl(user.getUrl());
////            _user.setStatus(user.getStatus());
////            _user.setPrivate(user.isPrivate());
////            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
////        } else {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////    }
//
//    @DeleteMapping("/Users/{id}")
//    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") String userId) {
//        Optional<UserVO> userData = userRepository.getByUserId(userId);
//
//        try {
//            userRepository.deleteByUserId(userId);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//        }
//    }
//
//    @DeleteMapping("/Users")
//    public ResponseEntity<HttpStatus> deleteAllUsers() {
//        try {
//            userRepository.deleteAll();
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
//        }
//    }
//}
package org.kitchen.booting.controller;

import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.service.ProfileService;
import org.kitchen.booting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ProfileService profileService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/register")
    public String userForm(@ModelAttribute User user, Model model){
        //model.addAttribute("user", user);
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String userSubmit(@ModelAttribute User user){
        userService.save(user);
        return "/index";
    }

    @GetMapping("/user/list")
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @GetMapping("/user/delete/{userId}")
    public String delete(@PathVariable("userId") String userId){
        userService.delete(userId);
        return "redirect:/user/list";
    }
}