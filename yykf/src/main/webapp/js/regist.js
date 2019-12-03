var wait=60;
function time(o){
    if (wait == 0) {
        o.removeAttribute("disabled");
        o.value="免费获取验证码";
        wait = 60;
    } else {

        o.setAttribute("disabled", true);
        o.value="重新发送(" + wait + ")";
        wait--;
        setTimeout(function() {
                time(o)
            },
            1000)
    }
}


function sendSMS(){
    alert(111);
    var btn = document.getElementById('btn');
    time(btn);

    var phoneNum = document.getElementById('phoneNum').value;//获取页面的手机号
    //通过ajax传递数据到servlet

    alert(phoneNum);
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


}