    $(document).ready(function () {
        // let scrapDiv = $("#scrapDiv");

        $(document).on("click", "#create-scrap", (function (event) {
            btn_scrapsubmit(true);
        }));

        $(document).on("click", "#delete-scrap", (function (event) {
            btn_scrapsubmit(false);
        }));

        $(document).on("click", "#create-like", (function (event) {
            btn_likesubmit(true);
        }));

        $(document).on("click", "#delete-like", (function (event) {
            btn_likesubmit(false);
        }));
    })

function btn_likesubmit(flag) {
    var likeId = {};
    likeId["profile"] = $("#userId").val();
    likeId["recipe"] = $("#recipeNo").val();

    // 로그인 안한 사용자가 팔로우 누르면 return
    if ($("#userId").val() == null) {
        alert('로그인이 필요합니다');
        return;
    }

    if (flag) {
        // 빈칸하트를 누르면
        console.log("좋아요 저장 : ", flag);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/recipe/saveLikeAjax",
            data: JSON.stringify(likeId),
            async : false,
            dataType: 'json',
            success: function (response) {
                    console.log("RESPONSE 좋아요 : ", response);
                    console.log(response[0]);
                    console.log(response.length);
                    likeDiv.innerHTML = "";
                    likeDiv.innerHTML = "<i id='delete-like'class='fa fa-heart' aria-hidden='true' style='color: crimson'></i>&nbsp;";
                    $(".countDiv").empty();
                    if(response.length === 1)
                    {
                        $(".countDiv").append(response[0] +"님이 좋아합니다");
                    }
                    else
                    {
                        $("#tmp").remove();
                        $(".countDiv").append(response[0] +"님 외 "+ --response.length +"명이 좋아합니다");
                    }
                }
        });
    } else {
        // 찜 취소를 누르면
        console.log("좋아요 삭제 : ", flag);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/recipe/deleteLikeAjax",
            data: JSON.stringify(likeId),
            async: false,
            dataType: 'json',
            success: function (response) {
                    console.log("RESPONSE좋아요 취소 : ", response);
                    console.log(response[0]);
                    console.log(response.length);
                    likeDiv.innerHTML = "";
                    likeDiv.innerHTML = "<i id='create-like' class='fa fa-heart-o' aria-hidden='true'></i>&nbsp;";
                    $(".countDiv").empty();
                    if(response.length === 1)
                    {
                        $(".countDiv").append(response[0] +"님이 좋아합니다");
                    }
                    else if(response.length ===0)
                    {
                        $(".countDiv").append("첫번째 좋아요의 주인공이 되어보세요!");
                    }
                    else
                    {
                        $("#tmp").remove();
                        $(".countDiv").append(response[0] +"님 외 "+ --response.length +"명이 좋아합니다");
                    }
                }
        });
    }
}

function get_scrap() {
    return $.ajax({
        type: "GET",
        url: "/recipe/goScrap/" + $("#userId").val() + "/" + $("#recipeNo").val(),
        contentType: 'application/json'
    });
}

function btn_scrapsubmit(flag) {
    var scrapId = {};
    scrapId["profile"] = $("#userId").val();
    scrapId["recipe"] = $("#recipeNo").val();

    // 로그인 안한 사용자가 팔로우 누르면 return
    if ($("#userId").val() == null) {
        alert('로그인이 필요합니다');
        return;
    }

    if (flag) {
        // 찜하기를 누르면
        console.log("저장 : ", flag);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/recipe/saveScrapAjax",
            data: JSON.stringify(scrapId),
            dataType: 'json',
            success:
                get_scrap().then(function (response) {
                    console.log("RESPONSE찜하기 : ", response);
                    scrapDiv.innerHTML = "";
                    scrapDiv.innerHTML = "<i id='delete-scrap' class='fa fa-folder' style='font-size: 30px; display: inline-flex' aria-hidden='true'><p style='margin-top:3px;' class='scrapT'>스크랩취소</p></i>";
                })
        });
    } else {
        // 찜 취소를 누르면
        console.log("삭제 : ", flag);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/recipe/deleteScrapAjax",
            data: JSON.stringify(scrapId),
            dataType: 'json',
            success:
                get_scrap().then(function (response) {
                    console.log("RESPONSE찜취소 : ", response);
                    scrapDiv.innerHTML = "";
                    scrapDiv.innerHTML = "<i id='create-scrap' class='fa fa-folder-open-o' style='font-size: 30px; display: inline-flex' aria-hidden='true'><p class='scrapT'>스크랩</p></i>";
                })
        });
    }
}
    // 코멘트 버튼이 코멘트 창으로 바로 가게
    function fnMove(){
        var offset = $(".comments-container").offset();
        $('html, body').animate({scrollTop : offset.top}, 400);
    }

