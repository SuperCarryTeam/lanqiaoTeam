$(document).ready(function () {
    $("dd>a").click(function (e) {
        e.preventDefault();
        $("#iframeMain").attr("src",$(this).attr("href"));
    })  ;
});