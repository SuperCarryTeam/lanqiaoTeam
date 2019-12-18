var vue = new Vue({
    el:'#vueTable',
    data:{
        products:null
    }
});

//layUI分页
layui.use('laypage',function () {
    var laypage = layui.laypage;

    laypage.render({
        elem:"pagecontainer",
        count:totalCount,
        limit:8,
        layout:['count','prev','page','next','limit','refresh','skip'],
        jump:function (obj, first) {
            //首次不执行
            if(!first){
                getData(obj.curr , obj.limit);
            }
        }
    })
})

var totalCount = 0;
var currPage = 1;//首次加载，当前页是1
var pageSize = 8;

getData(currPage,pageSize);

function getData(currPage , pageSize) {

    // axios
    //     .get("/lanqiao1121/products.do",{
    //         params:{
    //             currPage:currPage,
    //             pageSize:pageSize
    //         }
    //     })
    //     .then(function (response) {
    //         vue.products = response.data.data;
    //         totalCount = response.data.totalCount;
    //         //alert(totalCount);
    //     })
    //     .catch(function (error) {
    //         console.log(error);
    //     })
    var params = {
        currPage:currPage,
        pageSize:pageSize
    };
    $.ajax('/NewProject/products.do',{
        type: 'POST',
        url: '/NewProject/products.do',
        dataType: 'json',
        data: params,
        async: false,
        success: function (response) {
            vue.products = response.data;
            totalCount = response.totalCount;
        }
    });

}