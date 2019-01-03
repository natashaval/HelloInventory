$(document).ready(function (){
    categoryDataTable();
addCategory();
categoryButton();
});

var categoryTable;

function fetchCategory(){
    $.ajax({
        async: true,
        type : "GET",
        url: "/clerk/category2/all",
        success : function(data){
            $(".category-table").html(data);

        }
    });
}

function addCategory() {
    $('#category-add-form').submit(function (e) {
        e.preventDefault();
        var frm = $("#category-add-form");
        var data = {};

        data['name'] = $('#category-name').val();
        data['description'] = $('#category-description').val();

        $.ajax({
            contentType: "application/json; charset=utf-8",
            type: "POST",
            url: "/clerk/category2/add",
            dataType: 'json',
            data: JSON.stringify(data),
            success: function (data) {

                $('#category-name').val("");
                $('#category-description').val("");

                toastr.success("Category has been saved!", data.status, {
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

function categoryButton() {
    // Open Category Details Modal
    $('#category-table tbody').on('click', '.category-details', function (e) {
        categoryRow = categoryTable.row($(this).parents('tr')).data();
        categoryId = categoryRow.id;

        $('#modal-category-details').modal('show');

        $.ajax({
            type: "GET",
            url: "/clerk/category2/" + categoryId,
            success: function (result) {
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
        categoryId = categoryRow.id;

        $('#modal-category-edit').modal('show');

        $.ajax({
            type: "GET",
            url: "/clerk/category2/" + categoryId,
            success: function (result) {
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
            data['name'] = $('#category-edit-name').val();
            data['description'] = $('#category-edit-description').val();

            $.ajax({
                contentType: "application/json; charset=utf-8",
                type: "PUT",
                url: "/clerk/category2/" + categoryId,
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (data) {
                    $('#modal-category-edit').removeAttr("data-category-id");


                    $('#modal-category-edit').modal('hide').data('bs.modal', null); // close modal
                    if (data.status == "Updated") {
                        toastr.success("Category has been updated!", data.status, {
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
                error: function(jqXHR, textStatus, errorThrown){
                    $('.modal-category-edit-title').html("<strong>Error</strong>");
                    errorJSON = jQuery.parseJSON(jqXHR.responseText);
                    alert(errorJSON.message);
                    toastr.error(errorJSON.message, "Failed", {
                        closeButton: true,
                        progressBar: true
                    });
                }
            });
        });
    });

    $('#category-table tbody').on('click', '.category-delete', function (e) {
        e.preventDefault();
        categoryRow = categoryTable.row($(this).parents('tr')).data();
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
                        $.ajax({
                            type: "DELETE",
                            url: "/clerk/category2/" + categoryId,
                            contentType: 'application/json; charset=utf-8',
                            success: function(data){
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
                            error: function(jqXHR, textStatus, errorThrown){
                                errorJSON = jQuery.parseJSON(jqXHR.responseText);
                                toastr.error(errorJSON.message, "Error", {
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

