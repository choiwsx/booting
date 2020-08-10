var working = false;
function openSearch(){
    if(working) return;
    working=true;
    var str ="";
    str += "<div class=\"searchDiv\">\n" +
        "            <div class=\"form_div  float_parent\">\n" +
        "                <form class=\"search_form\" action=\"/list\" method=\"get\">\n" +
        "                    <i class=\"fa fa-search\" aria-hidden=\"true\"></i>\n" +
        "                    <input placeholder=\" Search\" class=\"search_keyword\" name=\"keyword\">\n" +
        "                    <button type=\"button\" class=\"search_close\" onclick='closeClick()'>\n" +
        "                        <i class=\"fa fa-times\" aria-hidden=\"true\"></i>\n" +
        "                   </button>\n" +
        "                </form>\n" +
        "            </div>\n" +
        "        </div>";
    var tmp = $(".header-container");
    tmp.append(str);
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
        working=false;
    },1000);
}

function closeClick(){
    console.log("여기는 옴");
    if(working) return false;
    $(".searchDiv").remove();
}