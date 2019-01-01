var tableSerial;
$(document).ready(function(){

    tableSerial = $('#item-serial-table').DataTable({
        ajax: "/clerk/item/" + itemId + "/serialJSON",
        // dataSrc: "data",
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

// https://www.sanwebe.com/snippet/add-and-remove-fields-dynamic-and-simple-with-jquery
// https://www.codexworld.com/add-remove-input-fields-dynamically-using-jquery/
// https://www.sanwebe.com/2013/03/addremove-input-fields-dynamically-with-jquery
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

// https://stackoverflow.com/questions/26555928/ajax-send-value-of-dynamically-created-input-boxes

$('#serial-add-form').submit(function (e) {
    e.preventDefault();
    var serialFormData = $(this).serializeArray();
    console.log(serialFormData);

    $.ajax({
        method: "POST",
        url: "/clerk/item/" + itemId + "/serial",
        data: serialFormData,
        success: function(data){
            // window.location.reload();
            // $('.serialResult').append("<p style='color: green;'>Success</p>");
            toastr.success(data.data, data.status, {
                closeButton: true,
                progressBar: true
            });
            $('#item-serial-table').DataTable().ajax.reload();
            $('#serial-add-form')[0].reset();
        },
        error: function (e){
            // $('.serialResult').append("<p style='color: red;'>Error</p>");
            toastr.error(e.message, "Error", {
                closeButton: true,
                progressBar: true
            });
        }
    });

});

function getItemSerial(itemId){
    var serialURL = "/clerk/item/" + itemId + "/serialJSON";
    $.getJSON(serialURL, function(data){
        $.each(data.data, function(i, value){
            $('#item-serial-list').append("<p>" + value.serialId + "</p>");
        });
    });
}

function buttonView() {
// https://www.w3schools.com/jquery/jquery_slide.asp
    $('#serial-add-button').click(function () {
        $('.serial-add-div').slideToggle();
    });

    $('#serial-assign-button').click(function () {
        console.log("Clicked");
        $('.serial-assign-div').slideToggle();
    });

    // https://stackoverflow.com/questions/31327933/how-add-more-then-one-button-in-each-row-in-jquery-datatables-and-how-to-apply-e
    $('#item-serial-table tbody').on('click', '.serial-delete-button', function (e) {
            deleteRow = tableSerial.row($(this).parents('tr')).data();
            console.log(deleteRow);
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
                        console.log("delete has been clicked" + serialId);
                        $.ajax({
                            type: 'DELETE',
                            // url: "/clerk/item/serial/" + deleteRow.serialId + "/delete",
                            url: "/clerk/item/serial/" + deleteRow.serialId,
                            success: function (data) {
                                console.log(data.data);
                                // $('.serialResult').html("<p>"+ data.data + "</p>");
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
                                console.log(errorJSON.message);
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
        // data: data,
        success: function (data) {
            // https://stackoverflow.com/questions/733314/jquery-loop-over-json-result-from-ajax-success
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
    console.log(serialFormData);

    $.ajax({
        type: "POST",
        url: "/clerk/item/" + itemId + "/assign",
        data: serialFormData,
        success: function(data){
            console.log(data.data);
            // $('.serial-assign-result').html("<p>"+ data.data + "</p>");
            if (data.status == "Success") {
                console.log(data.data);
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
            console.log(errorJSON.message);
            toastr.error(errorJSON.message, "Error", {
                closeButton: true,
                progressBar: true
            });
        }
    });
});
