$(function(){
   getItems();
});


$('#item-add-form').submit(function(e){
    e.preventDefault();
    var $form = $('#item-add-form');
    var fd = new FormData($(this)[0]);

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/clerk/item/add",
        data: fd,
        cache: false,
        processData: false,
        contentType: false,
        success: function(data){
            toastr.success(data.data, data.status,{
                closeButton: true,
                progressBar: true
            })
            $('#item-add-form').each(function () {
                this.reset();
            });

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


function getItems() {
    $.getJSON("/clerk/item/show", function (data) {
        $.each(data.data, function (i, value) {
            var items = [];
            items.push("<td>" + value.id + "</td>");
            items.push("<td>" + value.name + "</td>");
            items.push("<td>" + value.category.name + "</td>");
            items.push("<td>" + value.quantity + "</td>");
            items.push("<td>" +
                "<a class='btn btn-info item-details' href='/clerk/item/"+ value.id + "'><i class='far fa-list-alt'></i> Details</span></a> " +
                "<a class='btn btn-warning' href='/clerk/item/"+ value.id + "/serial'><i class=\"fas fa-barcode\"></i> Serial</span></a> " +
                "<button type='button' class='btn btn-danger item-delete' data-item-id='" + value.id + "' onclick='itemDelete(" + value.id + ")'><i class='fas fa-trash-alt'></i> Delete</span></button> " +
                "</td>");
            $("<tr/>", {
                "class": "item-list",
                html: items.join("")
            }).appendTo("#item-table > tbody");
        });

    });
}

$('.item-serial-enable').click(function(e) {
    e.preventDefault();
    var itemId = $(this).attr('data-item-id');
});

function itemDelete(itemId){
    bootbox.dialog({
        message: "Are you sure you want to Delete? ",
        title: "<i class='fas fa-trash-alt'></i> Delete Item",
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
                        url: "/clerk/item/" + itemId,
                        contentType: 'application/json; charset=utf-8',
                        success: function(result){
                            window.location.reload();
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
}
