/* ============================== *\
   => SEARCH
\* ============================== */


$(document).ready(function() {
	
	$('.navigation-bar__form .form__control').bind('focusin', function(e) {
		e.preventDefault();
		if(window.matchMedia("(min-width: 640px)").matches) {
			var trigger = $(this);
			var target = $(trigger).parents('.input-group');
			$(target).animate({
				width: "200%"
			}, 500);
		}
	});
	
	$('.navigation-bar__form .form__control').bind('focusout', function(e) {
		e.preventDefault();
		if(window.matchMedia("(min-width: 640px)").matches) {
			var trigger = $(this);
			var target = $(trigger).parents('.input-group');
			$(target).animate({
				width: "100%"
			}, 500);
		}
	});
	
});