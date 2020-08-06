package org.kitchen.booting.service;

import org.kitchen.booting.domain.Follow;
import org.kitchen.booting.domain.Profile;
import org.kitchen.booting.repository.FollowRepository;
import org.kitchen.booting.repository.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;
    @Autowired
    ProfileRepository profileRepository;

    private final Logger logger = LoggerFactory.getLogger(FollowService.class);

    public Follow get(String userId, String followUserId)
    {
        // 컨트롤러에서 내가 얘를 팔로우했는지 확인하기 위해서
        return followRepository.findByUserIdAndFollowUserId(userId, followUserId);
    }

    public List<Profile> findByUserId(String userId)
    {
        // 팔로잉 리스트
        // userId로 내가 팔로우한 사람들의 프로필객체 찾아오기
        List<Follow> follows = followRepository.findByUserIdAndStatusFalse(userId);
        List<String> followId = new ArrayList<>();
        List<Profile> followList = new ArrayList<>();
        follows.forEach(e -> followId.add(e.getFollowUserId()));
        followId.forEach(e -> followList.add(profileRepository.findByUserId(e)));

        return followList;
    }
    
    public List<Profile> findByFollowUserId(String followUserId)
    {
        // 팔로워 리스트
        // 나를 팔로우 한 사람들의 프로필객체 찾아오기
        List<Follow> followers = followRepository.findByFollowUserIdAndStatusFalse(followUserId);
        List<String> followerId = new ArrayList<>();
        List<Profile> followerList = new ArrayList<>();
        followers.forEach(e -> followerId.add(e.getUserId()));
        followerId.forEach(e -> followerList.add(profileRepository.findByUserId(e)));
        return followerList;
    }


    public List<Follow> followApply(String userId)
    {
        // 비공개계정일때 나한테 친구신청한 애들 찾는거
        return followRepository.findByFollowUserIdAndStatusTrue(userId);
    }

    public Follow getFollow(String userId, String followUserId)
    {
        return followRepository.findByUserIdAndFollowUserId(userId, followUserId);
    }

    public Boolean getPrivate(String followUserId) {
        // 팔로우 할 유저가 공개인지 비공개인지 확인
        // 0이면 true, 1(비공개) 이면 false
        Profile profile = profileRepository.findByUserId(followUserId);
        return profile.getIsPrivate();
    }

    public void delete(Follow follow) { followRepository.delete(follow); }

    public void save(Follow follow) { followRepository.save(follow); }
}
