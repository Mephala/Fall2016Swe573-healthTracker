// MENU MOBILE ===============================================================================
// Collpsable menu mobile and tablets
$("#menu-button-mobile").click(function () {
    $(".menu").slideToggle(400);
    $(this).toggleClass("active");
});

// MENU DROP DOWN ====================================== //
$(".menu .drop-down").click(function () {
    if ($(this).next("div").is(":visible")) {
        $(this).next("div").slideToggle("normal");
    } else {
        $(".menu .drop-down-container").fadeOut("fast");
        $(this).next("div").slideToggle("slow");

    }
});

<!-- Retina -->
$('#logo, #status').retinaReplace();

//Pace holder
$('input, textarea').placeholder();

<!-- Preloader -->
//<![CDATA[
$(window).load(function () { // makes sure the whole site is loaded
    $('#status').fadeOut(); // will first fade out the loading animation
    $('#preloader').delay(350).fadeOut('slow'); // will fade out the white DIV that covers the website.
    $('body').delay(350).css({'overflow': 'visible'});
})
//]]>

//accordion	
function toggleChevron(e) {
    "use strict";
    $(e.target)
        .prev('.panel-heading')
        .find("i.indicator")
        .toggleClass('icon-plus icon-minus');
}
$('#accordion').on('hidden.bs.collapse', toggleChevron);
$('#accordion').on('shown.bs.collapse', toggleChevron);
$('#accordion').on('hidden.bs.collapse', function () {
})