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
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: "/clerk/item/add",
        dataType: 'json',
        data: JSON.stringify(data),
        success: function(data) {
            $('#item-add-form').each(function () {
                // https://stackoverflow.com/questions/8701812/clear-form-after-submission-with-jquery
               this.reset();
            });
            // $(this).children('input').each(function() {
            //     $(this).val("");
            // });
            // fetchCategory();
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

$.getJSON("/clerk/item/show", function(data){
    console.log(data.data);

    $.each(data.data, function(i, value){
        var items = [];
        console.log('Data: ' + value.name);
        items.push("<td>"+ value.id + "</td>");
        items.push("<td>"+ value.name + "</td>");
        items.push("<td>"+ value.quantity + "</td>");
        items.push("<td>" +
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
                            fetchCategory();
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
