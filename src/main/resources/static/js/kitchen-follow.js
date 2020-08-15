$(document).ready(function () {
    $(document).on("click", "#create-follow", (function (event) {
        btn_followsubmit(true);
    }));

    $(document).on("click", "#delete-follow", (function (event) {
        btn_followsubmit(false);
    }));
});

function btn_followsubmit(flag) {
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
            dataType: 'json',
            success:
                get_follow().then(function (response) {
                    console.log("RESPONSE 팔로우 : ", response);
                    followDiv.innerHTML = "";
                    followDiv.innerHTML = "<input type='button' id='delete-follow' class='profile-btn' value='Unfollow'>";
                })
        });
    } else {
        // 팔로우 취소를 누르면
        console.log("팔로우 삭제 : ", flag);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/kitchen/deleteFollowAjax",
            data: JSON.stringify(followId),
            dataType: 'json',
            success:
                get_follow().then(function (response) {
                    console.log("RESPONSE 팔로우 취소 : ", response);
                    followDiv.innerHTML = "";
                    followDiv.innerHTML = "<input type='button' id='create-follow' class='profile-btn' value='Follow'>";
                })
        });
    }
}

function get_follow() {
    return $.ajax({
        type: "GET",
        url: "/kitchen/goFollow/" + $("#userId").val() + "/" + $("#followUserId").val(),
        contentType: 'application/json'
    });
}