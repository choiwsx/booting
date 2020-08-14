
function openSearch(){
    console.log('open')
    $('.side-icon').hide();
    $('.side-icon-close').show();
        var str = "";
        str += "<div class=\"searchDiv\">\n" +
            "            <div class=\"form_div  float_parent\">\n" +
            "                <form class=\"search_form\" action=\"/list\" method=\"get\">\n" +
            "                    <i class=\"fa fa-search\" aria-hidden=\"true\"></i>\n" +
            "                    <input placeholder=\" Search\" class=\"search_keyword\" onkeydown='result()' id=\"autocomplete\"  type=\"text\" name=\"keyword\">\n" +
            "                    <button type=\"button\" class=\"search_close\" onclick='closeClick()'>\n" +
            "                        <i class=\"fa fa-times\" aria-hidden=\"true\"></i>\n" +
            "                   </button>\n" +
            "                </form>\n" +
            "<br>" +
            "<div class='searchAuto'><h4 class='searchResult'>RESULTS</h4>" +
            "<div class='keyword'></div>"
            "            </div></div>\n" +
            "        </div>";
        var tmp = $(".header-container");
        tmp.append(str);
    $('#autocomplete').autocomplete({
        source : 'search'
    });
}

    function result() {
    var keyword = $('.search_keyword').val();
    $.ajax({
    url: '/searchlist',
    type: 'POST',
        contentType : "application/json;charset=utf-8",
    data: JSON.stringify(keyword),
        dataType : "text",
    success: function (result){
    console.log(result);
    // $('.keyword').html(result.userId);
},
    error: function (xhr, status, error) {
    console.log(error);
}
})
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

}