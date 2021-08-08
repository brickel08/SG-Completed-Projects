var DataService = function () {
	var self = this;

	self.getItems = function(callback, myError) {
		$.ajax({
			url: 'http://tsg-vending.herokuapp.com/items',
			method: 'GET',
			contentType: 'application/json',
			success: callback,
			error: myError
		});
	}

	self.vendItem = function(total, itemId, callback, myError) {
		$.ajax({
			url: 'http://tsg-vending.herokuapp.com/money/' + total + '/item/' + itemId,
			method: 'POST',
			contentType: 'application/json',
			success: callback,
			error: myError
		});
	}
}