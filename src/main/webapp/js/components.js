angular.module('components', [])
	.components('login', function() {
		return {
			restrict: 'E',
			transclude: true,
			scope: {},
			controller: function($scope, $element) {
				const user = encodeURIComponent($scope.username);
				const passwd = encodeURIComponent($scope.password);
				$scope.controller.login(user, passwd);
			},
			bindings: {
				controller: '=',
			},
			template: 
			``,
			 replace: true,
		}
	})