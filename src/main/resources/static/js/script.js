$(window).scroll(function () { 
  console.log(window.scrollY);
  var header = $(".header-background");
    if(window.scrollY>30) {
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
// 반응형 메뉴버튼 메뉴열기
function openMenu() {
    var x = document.getElementById("header-nav");
    if (x.className === "nav-container") {
      x.className += " responsive";
    } else {
      x.className = "nav-container";
    }
  }