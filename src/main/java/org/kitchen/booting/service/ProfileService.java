package org.kitchen.booting.service;

import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final Logger logger = LoggerFactory.getLogger(ProfileService.class);

    @Autowired
    private ProfileRepository profileRepository;

    public List<Profile> findAll(){
        return profileRepository.findAll();
    }

    public Optional<Profile> findById(String userId)
    {
        Optional<Profile> user = profileRepository.findById(userId);
        return user;
    }

    public Profile findByUserId(String userId)
    {
        Profile profile = profileRepository.findByUserId(userId);
        return profile;
    }

    public Profile save(Profile profile)
    {
        profileRepository.save(profile);
        return profile;
    }

    public void delete(String userId) {
        profileRepository.deleteById(userId);
    }

    public List<String> search(String keyword){
        return profileRepository.search(keyword);
    }

    public void saveFollow(String followerId, String followeeId) {
        Profile follower = profileRepository.findByUserId(followerId);
        Profile followee = profileRepository.findByUserId(followeeId);
        follower.addFollowing(followee);
        followee.addFollower(follower);
        save(follower);
//        save(followee);
    }

    public void deleteFollow(String followerId, String followeeId) {
        Profile follower = profileRepository.findByUserId(followerId);
        Profile followee = profileRepository.findByUserId(followeeId);
        follower.getFollowings().remove(followee);
        followee.getFollowers().remove(follower);
        save(follower);
//        save(followee);
    }
}