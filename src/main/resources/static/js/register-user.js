$("#user-form").submit(function(event){
    event.preventDefault();
    registerUser();
});


function registerUser(){
    var user ={};
    user["userId"] = $("#userId").val();
    user["password"] = $("#password").val();
    user["email"] = $("#email").val();
    user["nickname"] = $("#nickname").val();
    user["thumbnail"] = $("#thumbnailUrl").val();
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