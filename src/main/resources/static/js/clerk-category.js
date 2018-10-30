$(function (){
// alert("hello");
fetchCategory();
fetchCategoryRest();
});


function fetchCategory(){
    $.ajax({
        type : "GET",
        url: "/clerk/category2/all",
        success : function(data){
            // console.log(data);
            // $('#category-id').DataTable();
            $(".category-table").html(data);

        }
    })
}

// https://grokonez.com/java-integration/integrate-jquery-ajax-post-get-spring-boot-web-service
// https://medium.com/@gustavo.ponce.ch/spring-boot-jquery-datatables-a2e816e2b5e9
function fetchCategoryRest(){
    $('#getAllCustomerId').click(function(event){
       event.preventDefault();
       $.ajax({
           type: "GET",
           url : "/api/category/all",
           success: function(result){
               if (result.status == "Done"){
                   $('#getResultDiv ul').empty();
                   var custList = "";
                   // $.each(result.data, function(i, customer){
                   //     var customer = "- Customer with Id = " + i + ", firstname = " + customer.firstname + ", lastName = " + customer.lastname + "<br>";
                   //     $('#getResultDiv .list-group').append(customer)
                   // });
                   $.each(result.data, function (i, category) {
                       var category = "Category with Id = " + category.id + ", name = " + category.name + "<br />";
                       $('#getResultDiv .list-group').append(category);
                   })
                   console.log("Success: ", result);
               }else{
                   $("#getResultDiv").html("<strong>Error</strong>");
                   console.log("Fail: ", result);
               }
               },
           error : function (e) {
               $('#getResultDiv').html("<strong>Error</strong>");
           }

       });
    });

}
//
// $('#categoryModal').on('show.bs.modal', function(event){
//    var button = $(event.relatedTarget);
//    var recipient = button.data('whatever');
//    $.ajax({
//        type: "GET",
//        url: "/api/category/3",
//        success: function(result){
//            modal.find('.modal-title').text(result.data.name);
//            modal.find('.modal-body input').val(result.data.name);
//        }
//    });
// });
