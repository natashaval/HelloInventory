$(function(){
   // alert("clerk dashboard");

        // fetchCategory();
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