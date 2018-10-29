$(function(){
   // alert("clerk dashboard");
    $("#categoryList").click(function(){
    });
        fetchCategory();
});

function fetchCategory(){
    $.ajax({
        type : "GET",
        url: "/clerk/category2/all",
        success : function(data){
            console.log(data);
            $(".panel-body").html(data);
        }
    })
}