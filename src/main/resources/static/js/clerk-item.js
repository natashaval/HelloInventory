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
        success: function(response){
            console.info(response);
            $('#itemResult').text("Success in Adding Item");
            $('#item-add-form').each(function () {
                // https://stackoverflow.com/questions/8701812/clear-form-after-submission-with-jquery
                this.reset();
            });

        },
        error: function(e){
            $('#itemResult').text(e.ResponseText);
        }
    });


});


$('#btn-coba').click(function(){
    alert("COBA");
    $('#item-add-form').children('input, select').each(function(){
        var input = $(this);
        console.log('Type: ' + input.attr('type') + 'Name: ' + input.attr("name") + ' Value: ' + input.val());
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
                "<a class='btn btn-info item-details' href='/clerk/item/"+ value.id + "'><span class='glyphicon glyphicon-th-list'>Details</span></a>" +
                "<button type='button' class='btn btn-danger item-delete' data-item-id='" + value.id + "' onclick='itemDelete(" + value.id + ")'><span class='glyphicon glyphicon-trash'> Delete</span></button> " +
                // <button type="button" class="btn btn-danger category-delete" th:attr="data-category-id=${category.id}" ><span class="glyphicon glyphicon-trash"> Delete</span></button>
                "</td>");

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

$('.item-primary').click(function(e) {
    e.preventDefault();
    alert("clicked");
    var itemId = $(this).attr('data-item-id');
    console.log(itemId);
    /*
    e.preventDefault();

    var itemId = $(this).attr('data-category-id');
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
    */
});

function itemDelete(itemId){
    // var button = document.getElementsByClassName('item-delete');
    // var itemId = $(button).attr('data-item-id');
    // // var itemId = $(this).attr('data-item-id');
    console.log(itemId);

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
                    console.log("delete has been clicked" + itemId);


                    $.ajax({
                        type: "GET",
                        url: "/clerk/item/" + itemId + "/delete",
                        contentType: 'application/json; charset=utf-8',
                        success: function(result){
                            console.log(result);
                            // fetchCategory();
                            // getItems();
                            window.location.reload();
                        },
                        error: function(e){
                            bootbox.alert('Error in Delete!');
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
