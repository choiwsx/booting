$(document).ready(function () {
    $(document).on("click", "#create-follow", (function (event) {
        btn_followsubmit(true);
    }));

    $(document).on("click", "#delete-follow", (function (event) {
        btn_followsubmit(false);
    }));

});

function btn_followsubmit(flag) {
    // var followDiv = $(".followDiv");
    // var follower = $("#follower");
    var followId = {};
    followId["follower"] = $("#userId").val();
    followId["followee"] = $("#followUserId").val();
    // followDTO["status"] = $('input[name=status]').val();
    console.log($("#userId").val());
    console.log($("#followUserId").val());

    // 로그인 안한 사용자가 팔로우 누르면 return
    if ($("#userId").val() == null) {
        alert('로그인이 필요합니다');
        return;
    }

    if (flag) {
        // 팔로우를 누르면
        console.log("팔로우 저장 : ", flag);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/kitchen/saveFollowAjax",
            data: JSON.stringify(followId),
            async : false,
            dataType: 'json',
            success: function (response) {
                console.log("RESPONSE 팔로우 : ", response);
                followDiv.innerHTML = "";
                followDiv.innerHTML = "<input type='button' id='delete-follow' class='profile-btn' value='Unfollow'>";
                follower.innerHTML = "";
                follower.innerHTML = "<p>팔로워</p><p>"+response+"</p>"
            }
        });
    } else {
        // 팔로우 취소를 누르면
        console.log("팔로우 삭제 : ", flag);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/kitchen/deleteFollowAjax",
            data: JSON.stringify(followId),
            async : false,
            dataType: 'json',
            success: function (response) {
                console.log("RESPONSE 팔로우 : ", response);
                followDiv.innerHTML = "";
                followDiv.innerHTML = "<input type='button' id='create-follow' class='profile-btn' value='Follow'>";
                follower.innerHTML = "";
                follower.innerHTML = "<p>팔로워</p><p>"+response+"</p>"
            }
        });
    }
}
