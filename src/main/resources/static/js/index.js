$(document).ready(function () {
    const l = $(location).attr("pathname");
    const a = $('a[href="' + l + '"]')
    a.addClass('active')

    const li = a.closest('li.nav-item')
    li.addClass('active')

    a.closest('div.collapse').addClass('show')


    window.setTimeout(function () {
        $(".alerts.toast").fadeTo(500, 0).slideUp(500, function () {
            $(this).remove();
        });
    }, 10000);

    $('.close-alert').click(function () {
        $(this).parent().fadeOut("slow")
            .queue(function (nxt) {
                $(this).remove();
                nxt();
            });
    })
});
