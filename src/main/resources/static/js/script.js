var header = document.getElementsByClassName("header-background")[0];
$(window).scroll(function (t) { 
    if(t.scroll.y>30) {
        header.classList.add("trans");
        header.classList.add("solid");
        
        setTimeout(() => {
          header.classList.remove("trans");
        }, 500);

      } else {
        header.classList.remove("solid");
        header.classList.remove("trans");

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