$(document).ready(function (){
fetchCategory();
addCategory();
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

function addCategory() {
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
            success: function (data) {
                // bootbox.alert("Data has been successfully added");
                // $('#modal-category-add').modal('hide');
                $('#category-name').val("");
                $('#category-description').val("");
                fetchCategory();
                // $('#category-table').DataTable().ajax.reload();
            }
        });

    });
}