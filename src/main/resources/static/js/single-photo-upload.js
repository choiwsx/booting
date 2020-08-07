var regex = new RegExp("(.*?)\.(jpg|png|img)$");
var maxSize = 1048576;
$(document).ready(function(){
    $("#user-form").submit(function(event){
        event.preventDefault();

        $("#recipe-form").attr("action", "/recipe/list");
        $("#recipe-form").attr("method", "get");
        btn_ajaxsubmit();

    });
});

var filePath=$('input[type=file]');
$(document).on("change",".fileUploader",function(){
    upload(this);
});
function browseMainFile(){
    $("#uploadFile").click();
}
function upload(e){
    
    var formData = new FormData();
    var files = $("#uploadFile")[0].files;
    var uploadFileName = "uploadFile";
    if (!checkExtenstion(files[0].name, files[0].size)) {
        return false;
    }
    formData.append(uploadFileName, files[0]);
    
    $.ajax({
        url: '/upload/uploadAjaxAction',
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            setUploadedFile(result);
        }
    });
};

function checkExtenstion(fileName, fileSize){
    if(fileSize >= maxSize){
        alert("파일 사이즈 초과");
        return false;
    }
    if(!regex.test(fileName))
    {
        alert("해당 종류의 파일은 업로드할 수 없습니다.");
        return false;
    }
    return true;
}

function setUploadedFile(uploadResultArr)
{
    var str = "";
    var fileCallPath = "";

    $(uploadResultArr).each(function(i,obj){
        if(obj.image)
        {
            //fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
            fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.fileName);
            console.log(fileCallPath);
            str += "<data-path='"+obj.uploadPath+"'";
            str += " data-uuid='"+obj.uuid+"' data-file=\'"+fileCallPath+"\' data-type='image' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'" + "data-id='1'";
            str += ">";
            // str += "</li>";
            // str += "<span>"+obj.showFileName+"</span>";
            // str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' id='"+idx+"' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button></br>";
            // str += "<img src='/display?fileName="+fileCallPath+"'>";
        }
        else
        {
        }
    });
    $("#thumbnail")[0].src =  "/display?fileName="+fileCallPath;
    $("#thumbnailUrl")[0].value = fileCallPath;

}

function btn_ajaxsubmit(){

    var user ={};
    user["userId"] = $("#userId").val();
    user["password"] = $("#password").val();
    user["email"] = $("#email").val();
    user["nickname"] = $("#nickname").val();
    user["thumbnail"] = $("#thumbnailUrl").val();
    user["bio"] = $("#bio").val();
    user["isPrivate"] = $('input:checkbox[id="private"]').is(":checked") == true? "true":"false";


    console.log(JSON.stringify(user));
    $.ajax({
        type:"POST",
        contentType : "application/json",
        url : "/user/register",
        data : JSON.stringify(user),
        dataType : 'json',
        success:function(data){
            console.log("SUCESS: ", data );
            // $("#recipe-form").submit();
        }
    })

}