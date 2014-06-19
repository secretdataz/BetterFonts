$(".card-close-btn").click( function() {
	var cardToClose = $(this).closest(".closeable");
	cardToClose.css("position", "relative").animate({
		left: "15px",
		opacity: 0
	}, 300, function() {
		cardToClose.animate({
			height: "0px",
		}, 150, function() {
			cardToClose.css("display", "none");
		});
	});
});
