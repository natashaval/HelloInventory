$(function (){
// alert("hello");
fetchCategory();
fetchCategoryRest();
});


function fetchCategory(){
    $.ajax({
        async: true,
        type : "GET",
        url: "/clerk/category2/all",
        success : function(data){
            // console.log(data);
            // $('#category-id').DataTable();
            $(".category-table").html(data);

        }
    });
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

$('#category-add-form').submit(function (e) {
   e.preventDefault();
   console.log("Hello Submit New Category");
   var frm = $("#category-add-form");
   var data = {};
   // $.each(this, function(i, v){
   //     var input = $(v);
   //     data[input.attr("id")] = input.val();
   //     delete data["undefined"];
   // });
    data['name'] = $('#category-name').val();
    data['description'] = $('#category-description').val();
    console.log(data);

   $.ajax({
       contentType: "application/json; charset=utf-8",
       type: "POST",
       url: "/clerk/category2/add",
       dataType: 'json',
       data: JSON.stringify(data),
       success: function(data) {
           // bootbox.alert("Data has been successfully added");
           // $('#modal-category-add').modal('hide');
           $('#category-name').val("");
           $('#category-description').val("");
           fetchCategory();
       }
   });

});