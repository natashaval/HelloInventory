$(function(){
   getItems();
});

/*
$('#item-add-form').submit(function (e) {
    e.preventDefault();

    console.log("Submit item");
    var data = {};
    // from input type
    $(this).children('input').each(function(){
        var input = $(this);
        console.log('Type: ' + input.attr('type') + 'Name: ' + input.attr("name") + ' Value: ' + input.val());
        data[input.attr('name')] = input.val();
        delete data["undefined"];
    });
    // from select type
    $(this).children('select').each(function(){
       var select = $(this);
       var category = {};
       category["id"] = select.val();
       data["category"] = category;
    });

    console.log(data);



    $.ajax({
        // contentType: "application/json; charset=utf-8",
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/clerk/item/add",
        data: data,
        processData: false,
        // contentType: false,
        cache: false,
        timeout: 600000,

        // dataType: 'json',
        // data: JSON.stringify(data),


        success: function(data) {
            $('#itemResult').text("Success in Adding Item");
            console.log("SUCCESS: ", data);

            $('#item-add-form').each(function () {
                // https://stackoverflow.com/questions/8701812/clear-form-after-submission-with-jquery
               this.reset();
            });
            // $(this).children('input').each(function() {
            //     $(this).val("");
            // });
            // fetchCategory();
        },

        error: function(e){
            $('#itemResult').text(e.ResponseText);
            console.log("ERROR: ", e);
        }
    });



});
*/

/*
$('#itemSubmit').click(function(e){
   e.preventDefault();
   AjaxItemSubmit();
});


function AjaxItemSubmit(){
    var form = $('#item-add-form')[0];
    var itemData = new FormData(form);

    itemData.append("CustomField", "This is some extra testing");
    $('#itemSubmit').prop("disabled", true);

    console.log(itemData);
}
*/

// https://stackoverflow.com/questions/30162655/spring-mvc-ajax-file-upload-leading-to-415-unsupported-media-type
$('#item-add-form').submit(function(e){
    e.preventDefault();
    var $form = $('#item-add-form');
    var fd = new FormData($(this)[0]);
    console.info(fd);


    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/clerk/item/add",
        data: fd,
        cache: false,
        processData: false,
        contentType: false,
        success: function(data){
            // console.info(data);
            // $('#itemResult').append("<p class='text text-success'>Success in Adding Item</p>");
            toastr.success(data.data, data.status,{
                closeButton: true,
                progressBar: true
            })
            $('#item-add-form').each(function () {
                // https://stackoverflow.com/questions/8701812/clear-form-after-submission-with-jquery
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


// var item_table_data;
// $(document).ready(function(){
//     $('#item-table').DataTable();
//     item_table_data = $('#item-table-1').DataTable();
// });

// https://api.jquery.com/jquery.getjson/
function getItems() {
    $.getJSON("/clerk/item/show", function (data) {
        // console.log(data.data);

        $.each(data.data, function (i, value) {
            var items = [];
            console.log('Data Category: ' + value.category.name);
            items.push("<td>" + value.id + "</td>");
            items.push("<td>" + value.name + "</td>");
            items.push("<td>" + value.category.name + "</td>");
            items.push("<td>" + value.quantity + "</td>");
            items.push("<td>" +
                "<a class='btn btn-info item-details' href='/clerk/item/"+ value.id + "'><i class='far fa-list-alt'></i> Details</span></a> " +
                "<a class='btn btn-warning' href='/clerk/item/"+ value.id + "/serial'><i class=\"fas fa-barcode\"></i> Serial</span></a> " +
                "<button type='button' class='btn btn-danger item-delete' data-item-id='" + value.id + "' onclick='itemDelete(" + value.id + ")'><i class='fas fa-trash-alt'></i> Delete</span></button> " +
                // <button type="button" class="btn btn-danger category-delete" th:attr="data-category-id=${category.id}" ><span class="glyphicon glyphicon-trash"> Delete</span></button>
                "</td>");
            // items.push("<td>" +
            //     "<a class='btn btn-outline-warning item-serial-enable' th:if='value.itemType == ITEM' th:attr='data-item-id = "+ value.id + "'>Enabled</a>" +
            //     "<a class='btn btn-warning' th:if='value.itemType == ASSET' disabled='true'>Enabled</a>" +
            //     "<a class='btn btn-warning' href='/clerk/item/"+ value.id + "/assets'><span class='glyphicon glyphicon-plus-sign'>Assets</span></a> ");

            $("<tr/>", {
                "class": "item-list",
                html: items.join("")
            }).appendTo("#item-table > tbody");
        });

        /* // Data Tables
        $.each(data.data, function(i, value){
           var itema = [];
           itema.push(value.id);
           itema.push(value.name);
           itema.push(value.quantity);
           itema.push("Actions");
           return itema;
        });

        item_table_data.rows.add(itema);
        item_table_data.draw();
        */
    });
}

$('.item-serial-enable').click(function(e) {
    e.preventDefault();
    alert("clicked");
    var itemId = $(this).attr('data-item-id');
    console.log(itemId);

    /*
    $.ajax({
        type: "GET",
        url: "clerk/item/" + itemId + "/serial/enabled",
        success: function(data){
            window.location.reload();
            $('.item-result').appendChild("<div class='alert alert-warning alert-dismissible fade show' role='alert'>\n" +
                "  <strong>Holy guacamole!</strong> You should check in on some of those fields below.\n" +
                "  <button type='button' class='close' data-dismiss='alert' aria-label='Close'>\n" +
                "    <span aria-hidden='true'>&times;</span>\n" +
                "  </button>\n" +
                "</div>");
        },
        error: function(e){
            $('.item-result').append("<p>Failed to change Item to Assets</p>");
        }
    });
    */
});

function itemDelete(itemId){
    // var button = document.getElementsByClassName('item-delete');
    // var itemId = $(button).attr('data-item-id');
    // // var itemId = $(this).attr('data-item-id');
    console.log(itemId);

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
                    console.log("delete has been clicked " + itemId);


                    $.ajax({
                        type: "DELETE",
                        // url: "/clerk/item/" + itemId + "/delete",
                        url: "/clerk/item/" + itemId,
                        contentType: 'application/json; charset=utf-8',
                        success: function(result){
                            console.log(result);
                            // fetchCategory();
                            // getItems();
                            window.location.reload();
                        },
                        error: function(jqXHR, textStatus, errorThrown){
                            errorJSON = jQuery.parseJSON(jqXHR.responseText);
                            alert(errorJSON.message);
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

/*
function itemView(itemId){
    $.ajax({
        type: "GET",
        url: "/clerk/item/" + itemId,
        success: function(data){
            console.log(data);
            $('#item-show').hide();
            $('#item-details').html(data);
        },
        error: function(e){
            $('#item-details').html("Failed to view Item Details");
        }
    });
}
*/
