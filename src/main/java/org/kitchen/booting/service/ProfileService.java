package org.kitchen.booting.service;

import org.kitchen.booting.domain.Follow;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.domain.userauth.User;
import org.kitchen.booting.repository.FollowRepository;
import org.kitchen.booting.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final Logger logger = LoggerFactory.getLogger(ProfileService.class);
    private static final int BLOCK_PAGE_NUM_COUNT = 5;
    private static final int PAGE_POST_COUNT = 5;


    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private FollowRepository followRepository;

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
        Profile follower = profileRepository.findByUserId(followerId); // 팔로우 하는 아이
        Profile followee = profileRepository.findByUserId(followeeId); // 팔로우 당하는 아이
        Boolean status = followee.getIsPrivate(); // 팔로우 당하는 아이의 공개 비공개 상태

        Follow follow = new Follow(followee, follower, status);
        followRepository.save(follow);
    }

    public void deleteFollow(String followerId, String followeeId) {
        Profile follower = profileRepository.findByUserId(followerId); // 팔로우 하는 아이
        Profile followee = profileRepository.findByUserId(followeeId); // 팔로우 당하는 아이
        Follow follow = followRepository.findByFollowerAndFollowee(follower, followee);
        followRepository.delete(follow);
    }

    public List<Follow> realFollower(String followeeId) {
        Profile followee = profileRepository.findByUserId(followeeId); // 팔로우 당하는 아이

        return followRepository.findByFolloweeAndStatusIsTrue(followee);
    }

    public List<Follow> realFollowee(String followeeId) {
        Profile followee = profileRepository.findByUserId(followeeId);

        return followRepository.findByFollowerAndStatusIsTrue(followee);
    }
    // 비공개 계정이 아직 친구수락 안한 아이들 모음
    public List<Follow> yetFollow(String followeeId) {
        Profile followee = profileRepository.findByUserId(followeeId);

        return followRepository.findByFolloweeAndStatusIsFalse(followee);
    }



}