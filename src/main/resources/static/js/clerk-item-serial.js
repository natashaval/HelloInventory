var tableSerial;
$(document).ready(function(){
    // getItemSerial(itemId);
    tableSerial = $('#item-serial-table').DataTable({
        ajax: "/clerk/item/" + itemId + "/serialJSON",
        // dataSrc: "data",
        columns: [
            {"data": "serialId"},
            {"data" : "myUser.name"},
            {"data": "clerkId"},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: -1,
                data: null,
                defaultContent: "<button class='btn btn-danger serial-delete-button'>Delete</button>"
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
    console.log("Item Id: " + itemId);


    $.ajax({
        method: "POST",
        url: "/clerk/item/" + itemId + "/serial",
        data: serialFormData,
        success: function(response){
            // window.location.reload();
            // $('.serialResult').append("<p style='color: green;'>Success</p>");
            $('#item-serial-table').DataTable().ajax.reload();
            $('#serial-add-form')[0].reset();
        },
        error: function (e){
            $('.serialResult').append("<p style='color: red;'>Error</p>");
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
        $('.serial-assign-div').slideToggle();
    });

    // https://stackoverflow.com/questions/31327933/how-add-more-then-one-button-in-each-row-in-jquery-datatables-and-how-to-apply-e
    $('#item-serial-table tbody').on('click', '.serial-delete-button', function (e) {
        alert("Clicked Delete!");
        deleteRow = tableSerial.row($(this).parents('tr')).data();
        console.log(deleteRow);
    });
}

/* ITEM SERIAL ASSIGN TO EMPLOYEE */
function getEmployee() {
    $.ajax({
        dataType: 'json',
        url: '/clerk/employeelist',
        // data: data,
        success: function (data) {
            // console.log(data.data);
            // managerAndEmployee = data.data;

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
});
