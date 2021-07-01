// Call the dataTables jQuery plugin
$(document).ready(function() {
    let langUrl = '/js/json/' + $('html').attr('lang') + '.json'
    $('#dataTable')
        .DataTable({
            language: {
                url: langUrl
            }
        });
});
