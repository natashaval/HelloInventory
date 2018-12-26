$(document).ready(function (){
    categoryDataTable();
// fetchCategory();
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
                // fetchCategory();
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
                    "<button type='button' class='btn btn-info category-details' ><span class='glyphicon glyphicon-th-list'> Details</span></button>" +
                    " <button type='button' class='btn btn-primary category-edit'><span class='glyphicon glyphicon-edit'> Edit</span></button>" +
                    " <button type='button' class='btn btn-danger category-delete'><span class='glyphicon glyphicon-trash'> Delete</span></button>"
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
            title: "<span class='glyphicon glyphicon-trash'></span> Delete",
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
                            success: function(result){
                                console.log(result);
                                $('#category-table').DataTable().ajax.reload();
                            },
                            error: function(e){
                                bootbox.alert('Error in Delete!');
                            }
                        });

                    }
                }
            }
        });

    });
}

