//https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
//https://o7planning.org/en/11813/spring-boot-file-upload-with-jquery-ajax-example

$(document).ready(function(){
    $('#registerSubmit').click(function(event){
        // stop submit the form, post it manually
        event.preventDefault();
        ajaxPhoto();
    });

});

function ajaxPhoto() {
	var form = $('#registerEmployeeForm')[0];
	var data = new FormData(form);

	data.append("CustomField", "This is some extra data, testing");

    $('#registerSubmit').prop("disabled", true);

    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/admin/register",
        data: data,

        // prevent jQuery from automatically transforming the data into query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 60000,
        success: function (data) {
            $("#result").text(data);
            console.log("SUCCESS: ", data);
            $('#registerSubmit').prop("disabled", false);
        },

        error: function (e) {
            $("#result").text(e.responseText);
            console.log("ERROR: ", e);
            $('#registerSubmit').prop("disabled", false);
        }
    })
}