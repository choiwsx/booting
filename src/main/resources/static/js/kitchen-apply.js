    $(document).ready(function () {
        $(document).on("click", "#update-followDTO", (function (event) {
            btn_followsubmit(true);
        }));

        $(document).on("click", "#delete-followDTO", (function (event) {
            btn_followsubmit(false);
        }));
    });

function btn_followsubmit(flag) {
    if ($("#userId").val() == null) {
        alert('로그인이 필요합니다');
        return;
    }
    // var applyNum = $("")
    var followId = {};
    followId["follower"] = $("#follower").val();
    followId["followee"] = $("#followee").val();


    if (flag) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/kitchen/updateFollowAjax",
            data: JSON.stringify(followId),
            dataType: 'text'
            // success:
            //     get_follow().then(function (response) {
            //         console.log("RESPONSE 수락 : ", response);
            //         var tmp = '';
            //         for(i=1; i<response.size(); i++)
            //         {
            //             tmp += '<input type="hidden" id="followUserId" value='+ response[i].followUserId +'>';
            //             tmp += '<input type="hidden" id="userId" value='+ response[i].userId +'>';
            //             tmp += '<td><a href=""';
            //         }
            //         $(".applyDiv").html(tmp);
            //     })
        });
    } else {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/kitchen/deleteFollowAjax",
            data: JSON.stringify(followId),
            dataType: 'text'
            // success:
            //     get_follow().then(function (response) {
            //         console.log("RESPONSE 거절 : ", response);
            //         // followDiv.innerHTML = "";
            //     })
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