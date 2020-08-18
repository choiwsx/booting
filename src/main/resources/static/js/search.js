
var search_open = false;

function openSearch(){
    console.log('open')
    $('.side-icon').hide();
    $('.side-icon-close').show();
    var str = "";
    str += "<div class=\"searchDiv\">\n" +
        "            <div class=\"form_div  float_parent\">\n" +
        "                <form class=\"search_form\" action=\"/list\" method=\"get\">\n" +
        "                    <i class=\"fa fa-search\" aria-hidden=\"true\"></i>\n" +
        "                    <input placeholder=\" Search\" class=\"search_keyword\" onkeyup='searchKeyDown()' id=\"autocomplete\"  type=\"text\" name=\"keyword\">\n" +
        "                    <button type=\"button\" class=\"search_close\" onclick='closeClick()'>\n" +
        "                        <i class=\"fa fa-times\" aria-hidden=\"true\"></i>\n" +
        "                   </button>\n" +
        "                </form>\n" +
        "<br>" +
        "<div class='acContainer'><h4>RESULTS</h4>" +
        "<div class='acResults'></div>"
    "            </div></div>\n" +
    "        </div>";
    var tmp = $(".header-container");
    tmp.append(str);

    $('#autocomplete').autocomplete({
        source : 'search'
    });

    $(".header-background").addClass("solid");
    search_open=true;
}

function searchKeyDown() {
    if( $("#autocomplete").val().length > 1 ) {
        setTimeout(result(),2000);
    }
}

function result() {
    var keyword = $('.search_keyword').val();
    $.ajax({
        url: '/searchList',
        type: 'POST',
        data: {"keyword": keyword},
        dataType : 'json',
        success: function (result){
            var ac = '';
            $.each(result, function (key, value) {
                ac +=  '<a class=acResult href="/recipe/' + value.recipeNo + '">'
                if(value.thumbnail == ""){
                    ac += '<div class="acThumbnail"><img src="img/no-recipe-image.jpg"/></div>';
                }else{
                    ac += '<div class="acThumbnail"><img src="/display?fileName='+value.thumbnail+'" alt=""></div>';
                }
                ac += '<div class="acTitle"><p> '+ value.title + '</p></div>';
                ac += '</a>';
            });
            $('.acResults').html(ac);

        }, error : function (result) {
            console.log(result);
        }
    });
}

var  initStyles = {
    opacity : "0",
    transform : "translate3d(0px,-100vh,0px)"
};

$(".searchDiv").css(initStyles);
setTimeout(function(){
    var  styles = {
        opacity : "1",
        transform : "translate3d(0px,0px,0px)"
    };
    $(".searchDiv").css(styles);
},500);

function closeClick(){
    console.log("close");
    $(".searchDiv").remove();
    $('.side-icon').show();
    $('.side-icon-close').hide();
    search_open = false;
    if(typeof index=="undefined") {
        return;
    } else {
        if(scrollY <=30) {
            $(".header-background").removeClass("solid");
        }
    }

}