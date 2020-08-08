//jQuery time
var current_fs, next_fs, previous_fs; //fieldsets
var left, opacity, scale; //fieldset properties which we will animate
var animating; //flag to prevent quick multi-click glitches

function invalidNewUser(){
    var param = { "userId": $("#userId").val(), "email":$("#email").val() };
    // var email =  $("#email").val(); 
    // // var userId =  $("#userId").val(); 
    $.getJSON("/validate/userid", {userId:param.userId,email:param.email}, function(data) {
        if(data.result == 'success') {
            if(data.valid == 'true') {
                // ㅇㅋ
                return false;
            } else {
                // ㄴㄴ
                return true;
            }
        } else {
            // 오류
            alert("오류");
        }
        
    });
}

function invalidUserEmail(){
    var email =  $("#email").val(); 
    $.getJSON("/validate/email", {"email":email}, function(data) {
        if(data.result == 'success') {
            if(data.valid == 'true') {
                 // ㅇㅋ
                 return false;
            } else {
                // ㄴㄴ
                return true;
            }
        } else {
            // 오류
            alert("오류");
        }
        
    });
}

function nextFieldset(){
    animating = true;        
    current_fs = $(this).parent();
    next_fs = $(this).parent().next();
    
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
        }, 
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
        dataType : 'json',
        success:function(data){
            console.log("SUCESS: ", data );
        }
    })
};

$( document ).ready(function() {
    // All jquery dependant code here.
    $(".next.action-button").click(function(){
        console.log("next");
        if(animating) return false;


        if(invalidNewUser) {
            alert("아디 이멜중복");
            return false;
        }
        nextFieldset;

        
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