//jQuery time
var current_fs, next_fs, previous_fs; //fieldsets
var left, opacity, scale; //fieldset properties which we will animate
var animating; //flag to prevent quick multi-click glitches

function invalidNewUser(){
    var checkId = $("#userId").val();
    console.log("여긴오나"+checkId);
    $.getJSON("/user/validateId",{
        userId : checkId,
        ajax : 'true'
    }, function(data) {
        if(data.valid=='true')
        {
            invalidUserEmail();
        }else
        {
            alert("중복 아이디입니다, 다른 아이디 입력해주세요");
        }
    });
}

function invalidUserEmail(){
    var checkEmail = $("#email").val();
    console.log("여기는 왔어요");
    $.getJSON("/user/validateEmail",{
        email : checkEmail,
        ajax : 'true'
    }, function(data) {
        if(data.valid=='true')
        {
            nextFieldset();
        }
        else
        {
            alert("중복 이메일입니다, 다른 이메일을 입력해주세요");
        }
    });
}

function nextFieldset(){
    if(animating) return false;
    animating = true;        
    current_fs = $("#first_field");
    next_fs = $("#first_field").next();
    console.log("다음페이지");
    //activate next step on progressbar using the index of next_fs
    $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
    
    //show the next fieldset
    next_fs.show(); 
    //hide the current fieldset with style
    current_fs.animate({opacity: 0}, {
        step: function(now, mx) {
            //as the opacity of current_fs reduces to 0 - stored in "now"
            //1. scale current_fs down to 80%
            scale = 1 - (1 - now) * 0.2;
            //2. bring next_fs from the right(50%)
            left = (now * 50)+"%";
            //3. increase opacity of next_fs to 1 as it moves in
            opacity = 1 - now;
            current_fs.css({
        'transform': 'scale('+scale+')',
        'position': 'absolute'
        });
            next_fs.css({'left': left, 'opacity': opacity});
        }, 
        duration: 800, 
        complete: function(){
            current_fs.hide();
            animating = false;
        }
        //this comes from the custom easing plugin
        // easing: 'easeInOutBack'
    });
}

function lastField(){
    if(animating) return false;
    animating = true;
    current_fs = $("#second_field");
    next_fs = $("#second_field").next();
    console.log("다음페이지");
    //activate next step on progressbar using the index of next_fs
    $("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");

    //show the next fieldset
    next_fs.show();
    //hide the current fieldset with style
    current_fs.animate({opacity: 0}, {
        step: function(now, mx) {
            //as the opacity of current_fs reduces to 0 - stored in "now"
            //1. scale current_fs down to 80%
            scale = 1 - (1 - now) * 0.2;
            //2. bring next_fs from the right(50%)
            left = (now * 50)+"%";
            //3. increase opacity of next_fs to 1 as it moves in
            opacity = 1 - now;
            current_fs.css({
                'transform': 'scale('+scale+')',
                'position': 'absolute'
            });
            next_fs.css({'left': left, 'opacity': opacity});
        },
        duration: 800,
        complete: function(){
            current_fs.hide();
            animating = false;
        }
        //this comes from the custom easing plugin
        // easing: 'easeInOutBack'
    });
}

function registerUser(){
    var user ={};
    user["userId"] = $("#userId").val();
    user["password"] = $("#password").val();
    user["email"] = $("#email").val();
    user["nickname"] = $("#nickname").val();
    user["thumbnail"] = $("#single-thumbnailUrl").val();
    user["bio"] = $("#bio").val();
    user["isPrivate"] = $('input:checkbox[id="private"]').is(":checked") == true? "true":"false";
    $.ajax({
        type:"POST",
        contentType : "application/json",
        url : "/user/register",
        data : JSON.stringify(user),
        dataType : 'text',
        success:function(data){
            // lastField();
            window.location.href = "/login?join=true";
        }
    })
};

$( document ).ready(function() {
    // All jquery dependant code here.
    $(".next.action-button").click(function(){
        console.log("next");



        // if(invalidNewUser()) {
        //     alert("아디 이멜중복");
        //     return false;
        // }

       invalidNewUser();

        // nextFieldset;

        
    });
    
    $(".previous.action-button").click(function(){
        console.log("prev");
        if(animating) return false;
        animating = true;
        
        current_fs = $(this).parent();
        previous_fs = $(this).parent().prev();
        
        //de-activate current step on progressbar
        $("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
        
        //show the previous fieldset
        previous_fs.show(); 
        //hide the current fieldset with style
        current_fs.animate({opacity: 0}, {
            step: function(now, mx) {
                //as the opacity of current_fs reduces to 0 - stored in "now"
                //1. scale previous_fs from 80% to 100%
                scale = 0.8 + (1 - now) * 0.2;
                //2. take current_fs to the right(50%) - from 0%
                left = ((1-now) * 50)+"%";
                //3. increase opacity of previous_fs to 1 as it moves in
                opacity = 1 - now;
                current_fs.css({'left': left});
                previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});
            }, 
            duration: 800, 
            complete: function(){
                current_fs.hide();
                animating = false;
            }, 
            //this comes from the custom easing plugin
            // easing: 'easeInOutBack'
        });
    });
    
    $(".submit.action-button").click(function(){
        console.log("submit");
        event.preventDefault();
        registerUser();
    })
    
  });