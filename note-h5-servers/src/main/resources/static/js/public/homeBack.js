$(function(){
    var imgs = "<img src='/images/homeBack.png' class='goIndex' id='div2'/>";
    $("body").append(imgs);
    $('.goIndex').css({
        "width":"1.04rem",
        "height":"1.04rem",
        "z-index":"9999",
        "position":"fixed",
        "bottom":" 1.85rem",
        "right": "0.5rem",
        "opacity" :"0.5"
    })
    $('.goIndex').click(function(){
        window.location.href= "/index.html";
    });
   
})