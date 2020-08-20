// 인덱스 배경나타남

$(document).ready(function() {

  var dots = $(".art-background-dot");
  dots.addClass("dot-appear");

  $(".header-background").removeClass("solid");

  
  $(".dropdown-btn").hover(
    function() {         
      if(searchOpen) return;
        // $(this).children().css( "display", "block" );
        // if($(".header-background").hasClass("solid")) return;
        $(".header-background").removeClass("trans");
        $(".header-background").addClass("solid");
        $(this).children(".nav-inner-menu").css( "display", "block" );
    }, function() {
        // $(this).children().css( "display", "none" );
        // if($(".header-background").hasClass("solid")) return;
        if(searchOpen) return;
        $(this).children(".nav-inner-menu").css( "display", "none" );
        if(scrollY >30) return;
        $(".header-background").removeClass("trans");
        $(".header-background").removeClass("solid");
       
    }
  );
  $(".recipe-btn").hover(
    function() {         
      if(searchOpen) return;
        // $(this).children().css( "display", "block" );
        // if($(".header-background").hasClass("solid")) return;
        $(".header-background").removeClass("trans");
        $(".header-background").addClass("solid");
    }, function() {
        // $(this).children().css( "display", "none" );
        // if($(".header-background").hasClass("solid")) return;
        if(searchOpen) return;
        if(scrollY >30) return;
        $(".header-background").removeClass("trans");
        $(".header-background").removeClass("solid");
       
    }
  );

  $(".header-background, .nav-container, .header-container, .header-logo, .nav-side-container").hover(
    function() {         
      if(searchOpen) return;
        $(".header-background").removeClass("trans");
        $(".header-background").addClass("solid");

    }, function() {
        if(searchOpen) return;
        if(scrollY >30) return;
        $(".header-background").removeClass("trans");
        $(".header-background").removeClass("solid");

    }
  );
  

});

// 인덱스에서 텍스트 바꾸는 코드
 $(function() {
  //Get words from HTML
  var words = [];
  $(".art-txt-switch").each(function(index, value) {
      words.push($(value).text());
  });
  //Delete the words from HTML and place nr1
  $(".art-txt-switch-container")
      .empty()
      .append('<span class="art-txt animated-txt" data-scroll data-scroll-speed="10" data-scroll-position="top">' + words[0] + "</span>");

  //Counter to flip between the words
  var counterWords = 0;
  setInterval(function() {
      var tlm = new TimelineMax({});
      tlm.to($("span.art-txt-switch-container span.animated-txt"), 0.3, {
      ease: Expo.easeIn,
      top: "-1em",
      opacity: 0
      });
      tlm.set($("span.art-txt-switch-container span.animated-txt"), { top: "1em" });
      tlm.to($("span.art-txt-switch-container span.animated-txt"), 0.3, {
      ease: Expo.easeOut,
      top: "0em",
      opacity: 1
      });

      //delay to switch the content in the span (see animation on top)
      setTimeout(function() {
      counterWords++;
      if (counterWords >= words.length) {
          counterWords = 0;
      }
      //Insert new text
      $("span.art-txt-switch-container span.animated-txt").text(words[counterWords]);
      }, 300);
  }, 3000);
});


$('#index-tags').ready(function () {
  //rotation speed and timer
  var speed = 5000;
  
  var run = setInterval(rotate, speed);
  var textSlides = $('.textslide');
  var textContainer = $('#textslides ul');
  var elm = textContainer.find(':first-child').prop("tagName");
  var item_width = textContainer.width();
  var textPrevious = 'textprev'; //id of previous button
  var textNext = 'textnext'; //id of next button
  textSlides.width(item_width); //set the slides to the correct pixel width
  textContainer.parent().width(item_width);
  textContainer.width(textSlides.length * item_width); //set the slides container to the correct total width
  textContainer.find(elm + ':first').before(textContainer.find(elm + ':last'));
  resetSlides();
  
  
  //if user clicked on prev button
  
  $('#tag-buttons a').click(function (e) {
    //slide the item
    
    if (textContainer.is(':animated')) {
      return false;
    }
    if (e.target.id == textPrevious) {
      textContainer.stop().animate({
        'left': 0
      }, 1500, function () {
        textContainer.find(elm + ':first').before(textContainer.find(elm + ':last'));
        resetSlides();
      });
    }
    
    if (e.target.id == textNext) {
      textContainer.stop().animate({
        'left': item_width * -2
      }, 1500, function () {
        textContainer.find(elm + ':last').after(textContainer.find(elm + ':first'));
        resetSlides();
      });
    }
    
    //cancel the link behavior      
    return false;
    
  });
  
  //if mouse hover, pause the auto rotation, otherwise rotate it  
  textContainer.parent().mouseenter(function () {
    clearInterval(run);
  }).mouseleave(function () {
    run = setInterval(rotate, speed);
  });
  
  function resetSlides() {
    //and adjust the container so current is in the frame
    textContainer.css({
      'left': -1 * item_width
    });
  }
  
});

function rotate() {
  $('#textnext').click();
}




function openSearch(){
    console.log('open');
    searchOpen = true;
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
    if(scrollY <= 30) {
      console.log("30이하라 헤더 색붙이기");
      $(".header-background").addClass("solid");
      
  }
  

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
    console.log("close"+scrollY);
    $(".searchDiv").remove();
    $('.side-icon').show();
    $('.side-icon-close').hide();
    if(scrollY <= 30) {
      $(".header-background").removeClass("solid");
      
  }
  searchOpen = false;

}