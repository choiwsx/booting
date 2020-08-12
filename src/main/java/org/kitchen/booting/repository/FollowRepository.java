//package org.kitchen.booting.repository;
//
//import org.kitchen.booting.domain.FollowDTO;
//import org.kitchen.booting.domain.id.FollowId;
//import org.kitchen.booting.domain.userauth.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface FollowRepository extends JpaRepository<FollowDTO, FollowId> {
//    // 내가 구독한 사람들(팔로잉)의 아이디 리스트
//    public List<FollowDTO> findByUser(User user);
//    // 상태가 0인 애들 -> 진짜 친구가 된 애들
//    public List<FollowDTO> findByUserAndStatusFalse(User user);
//    // 나를 팔로우 한 사람들(팔로워)의 아이디 리스트
////    public List<FollowDTO> findByFollowUserId(User followUser);
//    // 상태가 0인 애들 -> 진짜 친구가 된 애들
//    public List<FollowDTO> findByFollowUserAndStatusFalse(User followUser);
//    // 내가 비공개계정일때 상태가 1인 애들 -> 나에게 팔로우 신청을 한 애들
//    public List<FollowDTO> findByFollowUserAndStatusTrue(User user);
//    public FollowDTO findByUserAndFollowUser(User user, User followUser);
//    public void delete(FollowDTO follow);
//}
