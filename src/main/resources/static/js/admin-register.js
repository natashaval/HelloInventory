//https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
//https://o7planning.org/en/11813/spring-boot-file-upload-with-jquery-ajax-example

$(document).ready(function(){
    // $('#registerSubmit').click(function(event){
    //     // stop submit the form, post it manually
    //     event.preventDefault();
    //     ajaxPhoto();
    // });
    seedManagerData();
    getManager();

});

function getManager(){
    // https://forum.jquery.com/topic/show-hide-div-elements-depening-on-select-value
    // https://stackoverflow.com/questions/11918397/jquery-hide-and-show-an-input-element
    $('#employee_role').change(function () {
        var roleId = $(this).val();
        if (roleId == 2) {
            $('#manager_name').show();
            $('#manager_id').show();
            autoCompleteManager();
        }
        else {
            $('#manager_name').hide();
            $('#manager_id').hide();
        }
    });

    $('#manager_name').hide();
    $('#manager_id').hide();
}

function autoCompleteManager(){
    options = {
        // url: "list/manager",
        // listLocation: "data",
        // getValue: "name",

        data: managerList,
        getValue: "name",
        list: {
            match: {
                enabled: true
            },

            onSelectItemEvent: function(){
                var value = $('#manager_name').getSelectedItemData().id;
                $('#manager_id').val(value).trigger("change");
            }
        }
    };

    console.log(options);

    $("#manager_name").easyAutocomplete(options);

}

var managerList = [];
function seedManagerData(){
    $.getJSON("/admin/list/manager", function(data){
        // console.log(data.data);
        $.each(data.data, function (key, val){
            var managerObj = {};
            managerObj["name"] = val.name;
            managerObj["id"] = val.id;
            managerList.push(managerObj);
        });
        console.log("Manager List: ");
        console.log(managerList);
    });
}