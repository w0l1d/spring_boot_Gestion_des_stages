$(document).ready(function() {
    $("#locales").change(function () {
        let selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace(window.location.href.split('?')[0] +'?lang=' + selectedOption);
        }
    });
});