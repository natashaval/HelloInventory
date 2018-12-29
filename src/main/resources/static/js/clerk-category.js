$(document).ready(function (){
    categoryDataTable();
// fetchCategory();
addCategory();
categoryButton();
cobaError();
});

var categoryTable;

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
                console.log(data);
                // bootbox.alert("Data has been successfully added");
                // $('#modal-category-add').modal('hide');
                $('#category-name').val("");
                $('#category-description').val("");
                // fetchCategory();
                toastr.success(data.data, data.status, {
                   closeButton: true,
                   progressBar: true,
                });
                $('#category-table').DataTable().ajax.reload();
            }
        });

    });
}

function categoryDataTable(){
    categoryTable = $('#category-table').DataTable({
       ajax: '/clerk/category2/all',
       //  ajax: "https://api.myjson.com/bins/wbhzw",
       columns: [
           {"data": "id"},
           {"data": "name"},
           {"data": "description"},
           {"data": null, "orderable": false}
       ],
        columnDefs: [
            {
                targets: -1,
                data: null,
                defaultContent:
                    "<button type='button' class='btn btn-info category-details' ><i class='far fa-list-alt'></i> Details</span></button>" +
                    " <button type='button' class='btn btn-primary category-edit'><i class='fas fa-edit'></i> Edit</span></button>" +
                    " <button type='button' class='btn btn-danger category-delete'><i class='fas fa-trash-alt'></i> Delete</span></button>"
            }
        ]
    });
}

// var categoryId;

function categoryButton() {
    // Open Category Details Modal
    $('#category-table tbody').on('click', '.category-details', function (e) {
        categoryRow = categoryTable.row($(this).parents('tr')).data();
        console.log(categoryRow);
        categoryId = categoryRow.id;

        $('#modal-category-details').modal('show');

        $.ajax({
            type: "GET",
            url: "/clerk/category2/" + categoryId,
            success: function (result) {
                console.log(result);
                if (result.status == 'Done') {
                    $('#modal-category-details-title').text("Category " + categoryId);
                    $('#category-details-name').val(result.data.name);
                    $('#category-details-description').val(result.data.description);
                }
            },
            error: function (e) {
                $('#modal-category-details modal-header').append("<strong>Data Error</strong>");
            }
        });
    });

    // Open Category Edit Modal
    $('#category-table tbody').on('click', '.category-edit', function (e) {
        categoryRow = categoryTable.row($(this).parents('tr')).data();
        console.log(categoryRow);
        categoryId = categoryRow.id;

        $('#modal-category-edit').modal('show');

        $.ajax({
            type: "GET",
            url: "/clerk/category2/" + categoryId,
            success: function (result) {
                console.log("AJAX GET EDIT FORM");
                console.log(result);
                if (result.status == 'Done') {
                    $('#modal-category-edit-title').text("Edit Category " + categoryId);
                    $('#category-edit-name').val(result.data.name);
                    $('#category-edit-description').val(result.data.description);
                }
            },
            error: function (e) {
                $('#modal-category-edit modal-header').append("<strong>Data Error</strong>");
            }
        });

        $('#category-edit-form').submit(function (e) {
            e.preventDefault();

            var data = {};
            console.log(categoryId);

            data['name'] = $('#category-edit-name').val();
            data['description'] = $('#category-edit-description').val();
            console.log(data);

            $.ajax({
                contentType: "application/json; charset=utf-8",
                type: "PUT",
                // url: "/clerk/category2/" + categoryId + "/edit",
                url: "/clerk/category2/" + categoryId,
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (data) {
                    console.log("success AJAX EDIT");
                    $('#modal-category-edit').removeAttr("data-category-id");

                    // https://stackoverflow.com/questions/29754902/close-bootstrap-modal-after-submit/33647143
                    $('#modal-category-edit').modal('hide').data('bs.modal', null); // close modal
                    // fetchCategory();
                    // table.ajax.reload();
                    if (data.status == "Updated") {
                        toastr.success(data.data, data.status, {
                            closeButton: true,
                            progressBar: true
                        });
                    }
                    else if (data.status == "Failed") {
                        toastr.error(data.data, data.status, {
                            closeButton: true,
                            progressBar: true
                        });
                    }
                    $('#category-table').DataTable().ajax.reload();
                },
                error: function (e) {
                    $('.modal-category-edit-title').html("<strong>Error</strong>");
                }
            });
        });
    });

    $('#category-table tbody').on('click', '.category-delete', function (e) {
        e.preventDefault();
        categoryRow = categoryTable.row($(this).parents('tr')).data();
        console.log(categoryRow);
        categoryId = categoryRow.id;

        bootbox.dialog({
            title: "<i class='fas fa-trash-alt'></i> Delete",
            message: "Are you sure you want to Delete? ",
            buttons: {
                success: {
                    label: "Cancel",
                    className: "btn-secondary",
                    callback: function(){
                        $('.bootbox').modal('hide');
                    }
                },
                danger: {
                    label: "Delete",
                    className: "btn-danger",
                    callback: function () {
                        console.log("delete has been clicked" + categoryId);
                        $.ajax({
                            type: "DELETE",
                            // url: "/clerk/category2/" + categoryId + "/delete",
                            url: "/clerk/category2/" + categoryId,
                            contentType: 'application/json; charset=utf-8',
                            success: function(data){
                                console.log(data);
                                if (data.status == "Deleted"){
                                    toastr.warning(data.data, data.status, {
                                        closeButton: true,
                                        progressBar: true
                                    });
                                }
                                else if (data.status == "Failed"){
                                    toastr.error(data.data, data.status, {
                                        closeButton: true,
                                        progressBar: true
                                    });
                                }
                                $('#category-table').DataTable().ajax.reload();
                            },
                            error: function(e){
                                bootbox.alert('Error in Delete!');
                                toastr.error(e, "Error", {
                                    closeButton: true,
                                    progressBar: true
                                });
                            }
                        });

                    }
                }
            }
        });

    });
}

function cobaError(){
    $.ajax({
        url: "/clerk/category2/8",
        type: "GET",
        success: function(data){
            $('#coba-error').append("<small>jika success category name = " + data.data.name + "</small>");
            alert(data.data.name);
        },
        // error: function(e){
        //     $('#coba-error').append(e.getMessage());
        // }
        // https://stackoverflow.com/questions/377644/jquery-ajax-error-handling-show-custom-exception-messages/450540
        // https://www.javacodegeeks.com/2012/11/spring-mvc-error-handling-example.html
        error: function(jqXHR, textStatus, errorThrown){
            errorJSON = jQuery.parseJSON(jqXHR.responseText);
            alert(errorJSON.message);
        }
    });
}

