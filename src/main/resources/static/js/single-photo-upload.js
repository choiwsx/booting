var regex = new RegExp("(.*?)\.(jpg|png|img)$");
var maxSize = 1048576;
var filePath=$('input[type=file]');
$(document).on("change",".fileUploader",function(){
    upload(this);
});
function browseMainFile(){
    $("#single-uploadFile").click();
}
function upload(e){    
    var formData = new FormData();
    var files = $("#single-uploadFile")[0].files;
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

function setUploadedFile(uploadResultArr){
    var str = "";
    var fileCallPath = "";

    $(uploadResultArr).each(function(i,obj){
        if(obj.image)
        {
            fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.fileName);
            console.log(fileCallPath);
            str += "<data-path='"+obj.uploadPath+"'";
            str += " data-uuid='"+obj.uuid+"' data-file=\'"+fileCallPath+"\' data-type='image' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'" + "data-id='1'";
            str += ">";

        }
        else
        {
        }
    });
    $("#single-thumbnail")[0].src =  "/display?fileName="+fileCallPath;
    $("#single-thumbnailUrl")[0].value = fileCallPath;

}