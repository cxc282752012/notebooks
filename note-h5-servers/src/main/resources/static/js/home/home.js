//跳转到注册页面
function registerDetail(){
    window.location.href= "/user/registerDetail.html";
}

//登陆
function login(){
	var userPhone = $("#userPhone").val();
	var password = $("#password").val();
    $.ajax({
        type: "POST",
        url: "/user/login.html",
		data:{
            userPhone:userPhone,
            password:password
		},
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log(data);
            var userMstEntity = data.userMstEntity;
            var info = data.info;
            if(!userMstEntity){
                alert(info);
            }else {
                alert("login successful!")
            }
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}