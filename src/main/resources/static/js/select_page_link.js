$(document).ready(function() {
    const l = $(location).attr("pathname");
    const a = $('a[href="' + l + '"]')
    a.addClass('active')

    const li = a.closest('li.nav-item')
    li.addClass('active')

    a.closest('div.collapse').addClass('show')


    $('#sidebar').addClass('toggled')
});
