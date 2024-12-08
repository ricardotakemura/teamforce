angular.module('components', ['controllers'])
	.component('login', {
		templateUrl: '../templates/login.htm',
		controller: 'LoginController',
	})
	.component('newuser', {
		templateUrl: '../templates/newuser.htm',
		controller: 'UserController',
	})
	.component('board', {
		templateUrl: '../templates/board.htm',
		controller: 'BoardController',
	})
	.component('card', {
		templateUrl: '../templates/card.htm',
	});