var ds = new DataService();
let total = 0;

// creating item buttons

function formatItem(item, index) {
	return `<button class="itemButtons" id="itemButton${index}" style="outline: none; cursor: crosshair;">
	<p class="cell"><b>${index + 1}</b></br>
	${item.name}</br>
	$${item.price}</br>
	Quantity: ${item.quantity}</p></button>`;
}


function refreshItems(items) {
	let displayItems = $("#items");
	displayItems.empty();


	for (let i = 0; i < items.length; i++) {
		const item = items[i];
		let formattedItem = formatItem(item, i);
		displayItems.append(formattedItem);
	}

	onItemClick(items);

}

// selecting an Item

function refreshSelection() {
	let selection = $("#itemNumber");
	selection.empty();
	$("#itemNumber").val('');
	$("#hiddenForm").val('');
}

function onItemClick(items) {

	$.each(items, function(index, item) {
		$("#itemButton" + index).on('click', function() {
			$("#itemNumber").val(index + 1);
			$("#hiddenForm").val(item.id);
			$("#change").empty();
		});
	})  
}

// adding Money

function refreshTotal() {
	let totalDisplay = $("#total");
	totalDisplay.empty();
	totalDisplay.val(total.toFixed(2));
}

function addDollar() {
	total += 1.00;
	refreshTotal();
}

function addQuarter() {
	total += 0.25;
	refreshTotal();
}

function addDime() {
	total += 0.10;
	refreshTotal();
}

function addNickel() {
	total += 0.05;
	refreshTotal();
}

function allClear() {
	total = 0.00;
	refreshTotal();
}

// making purchase

function onMakePurchase(e) {
	e.preventDefault();

	let total = $("#total").val();
	let itemId = $("#hiddenForm").val();

	ds.vendItem(total, itemId, onSuccess, onError);
}

function onSuccess(change) {
	formatChange(change);
	ds.getItems(refreshItems, onError);
	allClear();
	refreshSelection();
	$("#message").empty();
	$("#message").append('Thank you!!!');
}

function onError(myError) {
	let msg = $.parseJSON(myError.responseText);
	$("#message").empty();
	$("#message").append(msg.message);
}

// returning Change

function formatChange(change) {
	if (change.quarters != 0) {
		$("#change").append('Quarters: ' + change.quarters);
	}
	if (change.dimes != 0) {
		$("#change").append(' Dimes: ' + change.dimes);
	}
	if (change.nickels != 0) {
		$("#change").append(' Nickels: ' + change.nickels);
	}
	if (change.pennies != 0) {
		$("#change").append(' Pennies: ' + change.pennies);
	}
}

function onChangeReturnClick() {
	$("#change").empty();
	allClear();
	refreshMessage();
}

function refreshMessage() {
	$("#message").empty();
	$("#message").append('&#128125; Welcome to Ben\'s Vending Machine! &#128125;');
}


$(document).ready(function() {

	// getting all items
	var ds = new DataService();
	ds.getItems(refreshItems, onError);

	// add money buttons
	$("#addDollar").on('click', addDollar);
	$("#addQuarter").on('click', addQuarter);
	$("#addDime").on('click', addDime);
	$("#addNickel").on('click', addNickel);

	// making purchase
	$(document).on('click', '#makePurchase', onMakePurchase);

	// change return button
	$("#changeReturnButton").on('click', onChangeReturnClick);


});