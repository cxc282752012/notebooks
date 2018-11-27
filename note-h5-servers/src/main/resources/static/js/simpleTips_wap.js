function openTips(showText)
{
    $(".tipsMask").remove();
    $('html,body').css('overflow','hidden');
    var str = "<div class='tipsMask'><div class='tips_content'><div class='empty_pop'><p class='tipsMaskText'></p>"+
        "<div class='ButtonTwo'><input type='text' class='tipsYes' value='Confirm' onfocus='this.blur()'/>"+
        "</div></div></div></div>";
    $("body").append(str);
    $(".tipsMaskText").html("<span>" + showText + "</span>");
    $(".tipsYes").click(function(){
        $(".tipsMask").hide();
        $('html,body').css('overflow','auto');
    });
}

function openTipsSecond(showTextSecond){
    $(".tipsMaskSecond").remove();
    $('html,body').addClass('ovfHiden');
    var str = "<div class='tipsMaskSecond'><div class='tips_contentSecond'><div class='empty_popSecond'><p class='tipsMaskTextSecond'></p>"+
        "<div class='ButtonTwoSecond'><div class='tipsNoSecond'  onclik='javascript:cancelClick()'>Cancel</div><div class='tipsYesSecond'  onclik='javascript:confirmClick()'>Confirm</div>"+
        "</div></div></div></div>";
    $("body").append(str);
    $(".tipsMaskTextSecond").html("<span>" + showTextSecond + "</span>");
    $(".tipsYesSecond").click(function(){
        $(".tipsMaskSecond").hide();
        $('html,body').removeClass('ovfHiden');
    });
    $(".tipsNoSecond").click(function(){
        $(".tipsMaskSecond").hide();
        $('html,body').removeClass('ovfHiden');
    });
}
