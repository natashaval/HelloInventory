$(document).ready(function(){
    $('#category-id').DataTable();
});

$('.category-details').click(function(){
    // alert('Details ke klik');

    $('#modal-category-details').modal('show');
    var categoryId = $(this).attr('data-category-id');
    // alert('Details ke klik' + categoryId);
    console.log('category details ke klik, id = ' + categoryId);
    $.ajax({
        type: "GET",
        url: "/api/category/" + categoryId + "/edit",
        success: function(result){
            console.log(result);
            $('#modal-category-details-title').text("Category " + categoryId);
            $('#category-details-name').val(result.data.name);
            $('#category-details-description').val(result.data.description);
        },
        error : function(e){
            $('#modal-category-details modal-header').append("<strong>Data Error</strong>");
        }
    });

});

$('.category-edit').click(function() {
    $('#modal-category-edit').modal('show');
    var categoryId = $(this).attr('data-category-id');
    console.log('category EDIT klik, id = ' + categoryId);
    $.ajax({
        type: "GET",
        url: "/api/category/" + categoryId + "/edit",
        success: function(result){
            console.log(result);
            $('#modal-category-edit-title').text("Edit Category " + categoryId);
            $('#category-edit-name').val(result.data.name);
            $('#category-edit-description').val(result.data.description);
        },
        error : function(e){
            $('#modal-category-edit modal-header').append("<strong>Data Error</strong>");
        }
    });
});

$('.category-delete').click(function(e) {
    // $('#modal-category-delete').modal('show');
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
                        // type: 'DELETE',
                        // url: "/api/category/" + categoryId + "/delete",
                        type: "GET",
                        url: "/clerk/category2/" + categoryId + "/delete",
                        contentType: 'application/json; charset=utf-8',
                        success: function(result){
                            console.log(result);
                            // bootbox.alert(result.status);
                            // location.reload();
                            fetchCategory();
                            // parent.fadeOut('slow');
                        },
                        error: function(e){
                            // console.log(result);
                            // bootbox.alert(result.status);

                            bootbox.alert('Error in Delete!');
                            // location.reload();
                        }
                    })

                }
            }
        }
    })
});

// function deleteCategory(categoryId){
//     console.log('category DELETE klik, id = ' + categoryId);
// }
/*
$('.category-delete').click(function(){
    $('#modal-category-delete').modal('show');
    var categoryId = $(this).attr('data-category-id');
    console.log('category DELETE klik, id = ' + categoryId);
    $('#modal-category-delete').on("click", ".modal-category-delete-button", function(event){
        var $modalDiv = $(event.delegateTarget);
       console.log("this is clicked button" + categoryId);
    });
});
*/


/*
$('#modal-category-delete').on('show.bs.modal', function(event){
   $('.modal-category-delete-button').trigger("click");
});

$('.modal-category-delete-button').on("click", function(){
   alert('button inside modal clicked');
});
*/

/*
$('.category-delete').on('click', '.modal-category-delete-button', function(e){

    e.preventDefault();
    alert('Delete button has been clicked!');
    var categoryId = $(this).attr('data-category-id');
    console.log('category DELETE klik, id = ' + categoryId);

    $.ajax({
        type: "DELETE",
        url: "/api/category/" + categoryId + "/delete",
        success: function(result){
            console.log(result);
            alert(result.data);
        }
    })
*/
// if($("#modal-category-delete-button").data('clicked')){
//     alert('DELETE CLICKED');
// }

// $.ajax({
//     type: "GET",
//     url: "/api/category/" + categoryId + "/edit",
//     success: function(result){
//         console.log(result);
//         $('#modal-category-edit-title').text("Edit Category " + categoryId);
//         $('#category-edit-name').val(result.data.name);
//         $('#category-edit-description').val(result.data.description);
//     },
//     error : function(e){
//         $('#modal-category-edit modal-header').append("<strong>Data Error</strong>");
//     }
// });
// });