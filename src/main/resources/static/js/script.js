// 반응형 메뉴버튼 메뉴열기
function openMenu() {
  var x = document.getElementById("header-nav");
  if (x.className === "nav-container") {
    x.className += " responsive";
  } else {
    x.className = "nav-container";
  }
};

function scrollHeader() {
  console.log("scrollJEader");
};

document.addEventListener("DOMContentLoaded", function(){
  window.addEventListener('scroll', scrollHeader);
});


$(document).ready(function() {

  $("#js-scroll").on("scroll", function (t) {
    console.log("windowon");
    var header = document.getElementsByClassName("header-background")[0];
    if(t.scroll.y>30) {
      header.addClass("trans");
      header.addClass("solid");
      
      setTimeout(() => {
        header.removeClass("trans");
      }, 500);
  
    } else {
      header.removeClass("solid");
      header.removeClass("trans");
  
    }

  });

  $(window).scroll(
    function () { 
      console.log("Ddddd");
      console.log($(this).scrollTop())

      var header = $(".header-background");

      if($(this).scrollTop() > 30) { 
        header.addClass("trans");
        header.addClass("solid");
        setTimeout(() => {
          header.removeClass("trans");
        }, 500);
      } else {
        header.removeClass("solid");
        header.removeClass("trans");
      }
      console.log(window.scrollY);
  });

  $(".dropdown-menu").hover(
      function() {
          var index = $(this).index();
          console.log(index);
          $(this).children().css( "display", "block" );
          $(".header-background").removeClass("trans");
          $(".header-background").addClass("solid");
      }, function() {
          var index = $(this).index();
          console.log(index);
          $(this).children().css( "display", "none" );
          $(".header-background").removeClass("trans");
          $(".header-background").removeClass("solid");
      }
  );
});