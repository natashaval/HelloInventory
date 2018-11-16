$(document).ready(function(){
    $('#category-table').DataTable();
});

$('.category-details').click(function(){
    // alert('Details ke klik');

    $('#modal-category-details').modal('show');
    var categoryId = $(this).attr('data-category-id');
    // alert('Details ke klik' + categoryId);
    console.log('category details ke klik, id = ' + categoryId);
    $.ajax({
        type: "GET",
        url: "/clerk/category2/" + categoryId,
        success: function(result){
            console.log(result);
            if (result.status == 'Done') {
                $('#modal-category-details-title').text("Category " + categoryId);
                $('#category-details-name').val(result.data.name);
                $('#category-details-description').val(result.data.description);
            }
        },
        error : function(e){
            $('#modal-category-details modal-header').append("<strong>Data Error</strong>");
        }
    });

});

$('.category-edit').click(function() {
    // $('#modal-category-edit').modal('show');
    $('#modal-category-edit').modal('show');
    var categoryId = $(this).attr('data-category-id');
    // console.log('category EDIT klik, id = ' + categoryId);
    $('#modal-category-edit').attr("data-category-id",categoryId);
    var modalCategoryId = $('#modal-category-edit').attr('data-category-id');
    console.log('attr to modal category EDIT klik, id = ' + modalCategoryId);
    $.ajax({
        type: "GET",
        url: "/clerk/category2/" + categoryId,
        success: function(result){
            console.log("AJAX GET EDIT FORM");
            console.log(result);
            if (result.status == 'Done') {
                $('#modal-category-edit-title').text("Edit Category " + categoryId);
                $('#category-edit-name').val(result.data.name);
                $('#category-edit-description').val(result.data.description);
            }
        },
        error : function(e){
            $('#modal-category-edit modal-header').append("<strong>Data Error</strong>");
        }
    });
});

$('#category-edit-form').submit(function(e){
    e.preventDefault();
    console.log("Hello EDIT Category");
    var frm = $("#category-edit-form");
    var modalCategoryId = $('#modal-category-edit').attr('data-category-id');
    console.log('modal category EDIT klik, id = ' + modalCategoryId);
    // var categoryId = $(this).attr('data-category-id');
    // console.log('category EDIT klik, id = ' + categoryId);
    var data = {};

    data['name'] = $('#category-edit-name').val();
    data['description'] = $('#category-edit-description').val();
    console.log(data);


    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: "/clerk/category2/" + modalCategoryId + "/edit",
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(data) {
            console.log("success AJAX EDIT");
            $('#modal-category-edit').removeAttr("data-category-id");

            // https://stackoverflow.com/questions/29754902/close-bootstrap-modal-after-submit/33647143
            $('#modal-category-edit').modal('hide').data('bs.modal',null); // close modal
            fetchCategory();
        },
        error: function(e){
            $('.modal-category-edit-title').html("<strong>Error</strong>");
        }
    });
});

// https://coderwall.com/p/yo1i4q/clear-bootstrap-modal-content-after-close
// use to clear any input in modal (solved problem opening previous edit data after close and reopen another row)
$('body').on('hidden.bs.modal', '.modal', function(){
    $(this).removeData('bs.modal');
});

$('#modal-edit-button-close').click(function(e){
    $(this).parent("#modal-category-edit").modal('hide');
});

$('.category-delete').click(function(e) {
    e.preventDefault();

    var categoryId = $(this).attr('data-category-id');
    console.log('category DELETE klik, id = ' + categoryId);

    bootbox.dialog({
        message: "Are you sure you want to Delete? ",
        title: "<span class='glyphicon glyphicon-trash'></span> Delete",
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
                        type: "GET",
                        url: "/clerk/category2/" + categoryId + "/delete",
                        contentType: 'application/json; charset=utf-8',
                        success: function(result){
                            console.log(result);
                            fetchCategory();
                        },
                        error: function(e){
                            bootbox.alert('Error in Delete!');
                        }
                    });

                }
            }
        }
    })
});
