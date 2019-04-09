var tableSerial;
$(document).ready(function(){

    tableSerial = $('#item-serial-table').DataTable({
        ajax: "/clerk/item/" + itemId + "/serialJSON",
        columns: [
            {"data": "serialId"},
            {"data" : "myUserName"},
            {"data": "clerkId"},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: -1,
                data: null,
                defaultContent: "<button class='btn btn-danger serial-delete-button'><i class='fas fa-trash-alt'></i> Delete</button>"
            }
        ]
    });
    getEmployee();
    buttonView();
});

var serialDiv = $('.serial-add');
var itemId = $('#serial-itemId').attr('item-id');
var max_fields = 10;
var now_fields = 1;

/* ITEM SERIAL ADD */


$('#serial-input-add').click(function (e) {
    e.preventDefault();
    if(now_fields < max_fields){ // max input box allowed
        now_fields++;
        $(serialDiv).append("<div><input type='number' name='serials[]'/> <a href='#' class='remove_field'>Remove</a></div>");
        // add input box
    }
});

$(serialDiv).on("click", ".remove_field", function(e){
    e.preventDefault();
    $(this).parent('div').remove();
    now_fields--;

});

$('#serial-add-form').submit(function (e) {
    e.preventDefault();
    var serialFormData = $(this).serializeArray();

    $.ajax({
        method: "POST",
        url: "/clerk/item/" + itemId + "/serial",
        data: serialFormData,
        success: function(data){
            toastr.success(data.data, data.status, {
                closeButton: true,
                progressBar: true
            });
            $('#item-serial-table').DataTable().ajax.reload();
            $('#serial-add-form')[0].reset();
        },
        error: function(jqXHR, textStatus, errorThrown){
            errorJSON = jQuery.parseJSON(jqXHR.responseText);
            toastr.error(errorJSON.message, "Error", {
                closeButton: true,
                progressBar: true
            });
        }
    });
});

function buttonView() {
    $('#serial-add-button').click(function () {
        $('.serial-add-div').slideToggle();
    });

    $('#serial-assign-button').click(function () {
        $('.serial-assign-div').slideToggle();
    });

    $('#item-serial-table tbody').on('click', '.serial-delete-button', function (e) {
            deleteRow = tableSerial.row($(this).parents('tr')).data();
            serialId = deleteRow.serialId;

        bootbox.dialog({
            title: "<i class='fas fa-trash-alt'></i> Delete Serial",
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
                            type: 'DELETE',
                            url: "/clerk/item/serial/" + deleteRow.serialId,
                            success: function (data) {
                                if (data.status == "Deleted" ) {
                                    toastr.warning(data.data, data.status, {
                                        closeButton: true,
                                        progressBar: true
                                    });
                                }
                                $('#item-serial-table').DataTable().ajax.reload();
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

/* ITEM SERIAL ASSIGN TO EMPLOYEE */
function getEmployee() {
    $.ajax({
        dataType: 'json',
        type: "GET",
        url: '/clerk/employeelist',
        success: function (data) {

            $.each(data.data, function(key, value){
                $('#serial-assign').append(
                    "<option value='" + value.id + "'>" + value.name + "</option>"
                );
            });

        },
        error: function(e){
            console.log(e.stackTrace);
        }
    });
}

$('#serial-assign-form').submit(function(e){
    e.preventDefault();
    var serialFormData = $(this).serializeArray();

    $.ajax({
        type: "POST",
        url: "/clerk/item/" + itemId + "/assign",
        data: serialFormData,
        success: function(data){

            if (data.status == "Success") {

                toastr.success("Assign Item Serial to Employee Finished!", data.status, {
                    closeButton: true,
                    progressBar: true
                });
            }
            else if (data.status == "Failed"){
                toastr.error(data.data, data.status, {
                    closeButton: true,
                    progressBar: true
                })
            }
            $('#item-serial-table').DataTable().ajax.reload();
        },
        error: function(jqXHR, textStatus, errorThrown){
            errorJSON = jQuery.parseJSON(jqXHR.responseText);

            toastr.error(errorJSON.message, "Error", {
                closeButton: true,
                progressBar: true
            });
        }
    });
});
