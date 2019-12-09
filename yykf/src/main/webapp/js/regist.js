var countdown=60;
function settime(obj) {

    if (countdown == 0) {
        obj.removeAttribute("disabled");
        obj.value="获取验证码";
        countdown = 60;
        return;
    } else {
        obj.setAttribute("disabled", true);
        obj.value="重新发送(" + countdown + ")";
        countdown--;
    }
    setTimeout(function() {
            settime(obj) }
        ,1000)
}

function sendSms(obj){

    var phoneNum = document.getElementById('phoneNum').value;//获取页面的手机号
    //通过ajax传递数据到servlet

    $.ajax('http://localhost:8080/yykf/verification.do', {
        type: 'GET',
        url: 'http://localhost:8080/yykf/verification.do',
        dataType: 'json',
        data: {"phoneNum": phoneNum},
        async: true,
        success: function (result) {
            alert("验证码发送成功");
        }
    });

    settime(obj);
};