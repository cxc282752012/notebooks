(function (doc, win) {
    var docEl = doc.documentElement,
        resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
        recalc = function () {
            var clientWidth = docEl.clientWidth;
            if (!clientWidth) return;
            if(clientWidth>=1080){
                docEl.style.fontSize = '100px';
            }else{
                docEl.style.fontSize = 100 * (clientWidth / 1080) + 'px';
            }
        };
    if (!doc.addEventListener) return;
    recalc();
    win.addEventListener(resizeEvt, recalc, false);
    doc.addEventListener('DOMContentLoaded', recalc, false);
    document.onreadystatechange = function () {  
        if(document.readyState === "complete"){  
            $("#loading").fadeOut();  
        }  
    };
})(document, window);
//点击300ms延迟优化
// $(function(){
// 	logo();
// 	FastClick.attach(document.body);　
// 	if ('addEventListener' in document) {
// 		 document.addEventListener('DOMContentLoaded', function() {
// 		  FastClick.attach(document.body);
// 		 }, false);
// 	}
// 	fbMonitor();
// });

//页面logo
function logo(){
	var str = "" ;
	str = "<link rel='icon' href='/images/icon_logoTitle.ico' type='image/x-icon'/>";
	var txt = "";
	txt = "<link rel='apple-touch-icon' href='/images/icon_logo.png' />"
	$("head").append(str);
	$("head").append(txt);
}

//获取cookie信息 
function getCookie(c_name) {
	if (document.cookie.length>0) {
		c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1) { 
			c_start=c_start + c_name.length+1 
			c_end=document.cookie.indexOf(";",c_start)
			if (c_end==-1) c_end=document.cookie.length
			return unescape(document.cookie.substring(c_start,c_end))
		} 
	}
	return ""
}

//判断用户是否登录
function isCustomerLogin() {
	if(getCookie("customerId") != "") {
		return true;
	}
	return false;
}
//获取用户id
function isCustomerLogin() {
	var customerId=0;
	if(getCookie("customerId") != "") {
		customerId = getCookie("customerId");
	}
	return customerId;
}

function dateFormat(fmt,date)   
{ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}

function titleTxt(title){
	var str = "";
	str = "<div class='headerTitle'>" 
		+ 	"<a href='javascript:history.back(-1)' class='backIcon'><img src='/images/Button(return).png' /></a><p class='headerTitleTxt'>"+title+"</p>"
		+ "</div><div class='headerBox'></div>";
	//移动设备打开
//	if (browser.versions.mobile) {
//		//iOS浏览器打开
//		if(browser.versions.ios){
//			str = "<div class='headerTitle'>" 
//				+ 	"<a href='javascript:history.back(-1)'><img src='/images/Button(return).png' /></a><p class='headerTitleTxt'>"+title+"</p>"
//				+ "</div><div class='headerBox'></div>";
//		}
//		//Android浏览器打开
//		if(browser.versions.android){
//			str = "<div class='headerTitle'>" 
//				+ 	"<a href='javascript:history.back(-1)'><img src='/images/Button(return).png' /></a><p class='headerTitleTxt'>"+title+"</p>"
//				+ "</div><div class='headerBox'></div>";
//		}
//	}
	$("body").prepend(str);
}

//Facebook监测
function fbMonitor(){
    !function (f, b, e, v, n, t, s) {
        if (f.fbq) return; n = f.fbq = function () {
            n.callMethod ?
            n.callMethod.apply(n, arguments) : n.queue.push(arguments)
        };
        if (!f._fbq) f._fbq = n; n.push = n; n.loaded = !0; n.version = '2.0';
        n.queue = []; t = b.createElement(e); t.async = !0;
        t.src = v; s = b.getElementsByTagName(e)[0];
        s.parentNode.insertBefore(t, s)
    }(window, document, 'script',
        'https://connect.facebook.net/en_US/fbevents.js');
    fbq('init', '324120288123977');
    fbq('track', 'PageView');
}
