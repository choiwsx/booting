// 인덱스 배경나타남

$(document).ready(function() {

  var dots = $(".art-background-dot");
  dots.addClass("dot-appear");

  $(".header-background").removeClass("solid");

  
  $(".dropdown-btn, .recipe-btn").hover(
    function() {         
      if(search_open == true) return;
        // $(this).children().css( "display", "block" );
        // if($(".header-background").hasClass("solid")) return;
        $(".header-background").removeClass("trans");
        $(".header-background").addClass("solid");
        $(this).children().css( "display", "block" );
    }, function() {
        // $(this).children().css( "display", "none" );
        // if($(".header-background").hasClass("solid")) return;
        if(search_open == true) return;
        $(this).children().css( "display", "none" );
        if(scrollY >30) return;
        $(".header-background").removeClass("trans");
        $(".header-background").removeClass("solid");
       
    }
  );

  $(".header-background, .nav-container, .header-container, .header-logo, .nav-side-container").hover(
    function() {         
      if(search_open == true) return;
        $(".header-background").removeClass("trans");
        $(".header-background").addClass("solid");

    }, function() {
        if(search_open == true) return;
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
