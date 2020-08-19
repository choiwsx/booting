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

import org.kitchen.booting.domain.UserRegistrationDTO;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.exception.InvalidTokenRequestException;
import org.kitchen.booting.service.ProfileService;
import org.kitchen.booting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.MapsId;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ProfileService profileService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("register")
    public String userForm(@ModelAttribute UserRegistrationDTO userRegistrationDTO, Model model){
        //model.addAttribute("user", user);
        return "user/register";
    }

//    @PostMapping("register")
//    public String userSubmit(@ModelAttribute User user){
//        userService.save(user);
//        return "/index";
//    }



    @GetMapping("list")
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("delete/{userId}")
    public String delete(@PathVariable("userId") String userId){
        userService.delete(userId);
        return "redirect:/admin/user/list";
    }

    private final String valid = "{\"valid\":\"true\"}";
    private final String invalid = "{\"valid\":\"false\"}";

    @RequestMapping(value = "validateId", method=RequestMethod.GET)
    public @ResponseBody String validateUserId(@RequestParam(value = "userId", required = true) String userId) {
        if(userService.isValidNewUserId(userId))
        {
            return valid;
        }
        return invalid;
    }

    @RequestMapping(value="checkThumb", method = RequestMethod.GET)
    public @ResponseBody String checkThumbUser(@RequestParam(value = "userId", required = true) String userId)
    {

        User user = userService.findByUserId(userId);
        String tmpUrl = "{\"thumb\": \""+user.getProfile().getThumbnail()+"\"}";
        return tmpUrl;
    }


    @RequestMapping(value = "validateEmail", method=RequestMethod.GET)
    public @ResponseBody String validateUserEmail(@RequestParam(value = "email", required = true) String email) {
        if(userService.isvalidNewEmail(email))
        {
            return valid;
        }
        return invalid;
    }



    @GetMapping("/api/auth/registrationConfirmation")
    public String confirmRegistration(@RequestParam("token") String token) {
        logger.info("@@@token" + token);
        Optional<User> user = userService.confirmEmailRegistration(token);
        if(user.isPresent())
        {
            return "/login?verified=true";
        }
        return "/login?unverified=true";
    }
    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId){
        User user = userService.findByUserId(userId);
        String tmpUrl = "{\"thumb\": \""+user.getProfile().getThumbnail()+"\"}";

        return tmpUrl;
    }
    @GetMapping("/edit")
    public String editUserPassword(@AuthenticationPrincipal User user, Model model)
    {
        model.addAttribute("user", user);
        return "/user/edit";
    }
    @GetMapping("/report")
    public String reportUser()
    {
        return "/user/report";
    }

}