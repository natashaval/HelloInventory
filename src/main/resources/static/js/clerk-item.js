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