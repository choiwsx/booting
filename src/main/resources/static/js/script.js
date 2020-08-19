// 반응형 메뉴버튼 메뉴열기
function openMenu() {
  var x = document.getElementById("header-nav");
  if (x.className === "nav-container") {
    x.className += " responsive";
  } else {
    x.className = "nav-container";
  }
};

// function scrollHeader() {
//   console.log("scrollJEader");
// };

// document.addEventListener("DOMContentLoaded", function(){
//   window.addEventListener('scroll', scrollHeader);
// });


$(document).ready(function() {

    var interval = null;
    var interval2 = null;
    function startInterval(func, time)
    {
        interval = setInterval(func,time);
    }
    function startInterval2(func, time)
    {
        interval = setInterval(func,time);
    }
    function stopInterval(){
        clearInterval(interval);
        // clearInterval(interval2);
    }
    $(".dropdown-btn").hover(function(){
        //재생중이면 멈추고 null로 바꿔줌.
        if(interval!=null)
        {
            stopInterval();
            interval=null;
            interval2=null;
            startInterval2(followee, 3000);
            startInterval(popularTag, 3000);
        }else {
            followee();
            popularProfile();
            startInterval2(followee, 3000);
            popularTag();
            startInterval(popularTag, 3000);
        }
        // setInterval(popularTag,3000);
    }, function(){
        // clearInterval(intervalEvent);
        console.log("미우스 나감");
        // intervalEvent=0;
        stopInterval();
        clearInterval(interval2);
        interval=null;
    });
    // $(".li-Class").hover(function(){
    //     stopInterval();
    // })

    function followee(){
        $.ajax({
            type:'GET',
            contentType : 'application/json',
            url : '/followee',
            async : false,
            dataType: 'json',
            success:function(data){
                console.log('들어오나?');
                console.log(data);
                if(data === null) { return false; }

                var html="";
                var len = data.length;
                console.log(data);
                var random = [];
                random.push(Math.floor(Math.random()*len));
                while(true){
                    var ran = Math.floor(Math.random()*len);
                    if(len<5) {
                        break;
                    }
                    for(var i=0; i<random.length; i++)
                    {
                        if(random.includes(ran))
                        {
                            continue;
                        }
                        else
                        {
                            random.push(ran);
                            console.log(random);
                        }
                    }
                    if(random.length>4)
                        break;
                }
                if(len<5) {
                    html += '<li class="li-Class" style="height: 30px;"><a>더 많은 주방장을 구독해보세요!</a></li>';
                }else {
                    for (var i = 0; i < 5; i++) {
                        console.log(data[random[i]].nickname);
                        html += '<li class="li-Class" style="--animation-order: ' + i + '; height: 30px;"><a href="/kitchen/' + data[random[i]].userId + '">'
                            + data[random[i]].nickname + '</a></li>';
                    }
                }
                $(".followee").html(html);
            }
        });
    }

  function popularTag(){
      $.ajax({
          type:"GET",
          contentType : "application/json",
          url : "/popularTag",
          dataType : 'json',
          success:function(data){
              var html="";
              var len = data.length;
              console.log(data);
              var random = [];
              random.push(Math.floor(Math.random()*len));
              while(true){
                  if(len<5) {
                      break;
                  }
                  var ran = Math.floor(Math.random()*len);
                  for(var i=0; i<random.length; i++)
                  {
                      if(random.includes(ran))
                      {
                          continue;
                      }
                      else
                      {
                          random.push(ran);
                          console.log(random);
                      }
                  }
                  if(random.length>4)
                      break;
              }
              if(len<5) {
                  html += '<li class="li-Class""><a>더 많은 태그를 만들어보세요!</a></li>';
              }
              else {
                  for (var i = 0; i < 5; i++) {
                      console.log(data[random[i]].content);
                      html += '<li class="li-Class" style="--animation-order: ' + i + ';"><a href="/tag/get/' + data[random[i]].tagNo + '">'
                          + data[random[i]].content + '</a></li>';
                  }
              }
              $(".popularTag").html(html);
          }
          ,error:function(data)
          {
              console.log("error",data);
          }
      });
    }

    function popularProfile(){
        $.ajax({
            type:"GET",
            contentType : "application/json",
            url : "/popularProfile",
            dataType : 'json',
            success:function(data){
                var html="";
                var len = data.length;
                console.log(data);
                for (var i = 0; i < 3; i++) {
                    // console.log(data[i].content);
                    // html += '<li class="li-Class" style="--animation-order: ' + i + ';"><a href="/tag/get/' + data[random[i]].tagNo + '">'
                    //     + data[random[i]].content + '</a></li>';
                    html += "<span class=\"nav-inner-item\">\n" +
                        "                                                <span class=\"art-txt\">\n" +
                        "                                                    <a href='/kitchen/"+data[i].userId+"'>"+data[i].userId+"</a>\n" +
                        "                                                </span>\n" +
                        "                                            </span>";
                }
                $(".popularProfile").html(html);
            }
            ,error:function(data)
            {
                console.log("error",data);
            }
        });
    }




  $(".header-background").addClass("solid");

  // $("#js-scroll").on("scroll", function (t) {
  //   console.log("windowon");
  //   var header = document.getElementsByClassName("header-background")[0];
  //   if(t.scroll.y>30) {
  //     header.addClass("trans");
  //     header.addClass("solid");
      
  //     setTimeout(() => {
  //       header.removeClass("trans");
  //     }, 500);
  
  //   } else {
  //     header.removeClass("solid");
  //     header.removeClass("trans");
  
  //   }

  // });

  // $(window).scroll(
  //   function () { 
  //     // console.log("Ddddd");
  //     // console.log($(this).scrollTop())

  //     var header = $(".header-background");

  //     if($(this).scrollTop() > 30) { 
  //       header.addClass("trans");
  //       header.addClass("solid");
  //       setTimeout(() => {
  //         header.removeClass("trans");
  //       }, 500);
  //     } else {
  //       header.removeClass("solid");
  //       header.removeClass("trans");
  //     }
  //     // console.log(window.scrollY);
  // });

  $(".dropdown-btn").hover(
    function() {         
      if(search_open == true) return;
        // $(this).children().css( "display", "block" );
        // if($(".header-background").hasClass("solid")) return;
        $(this).children(".nav-inner-menu").css( "display", "block" );
    }, function() {
        // $(this).children().css( "display", "none" );
        // if($(".header-background").hasClass("solid")) return;
        if(search_open == true) return;
        $(this).children(".nav-inner-menu").css( "display", "none" );

       
    }
  );
});



