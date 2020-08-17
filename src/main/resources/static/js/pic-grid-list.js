$(window).on('load', function() {
  $('.post-module').on('hover', function() {
    $(this).find('.description').stop().animate({
      height: "toggle",
      opacity: "toggle"
    }, 300);
  });
});