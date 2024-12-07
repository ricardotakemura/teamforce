angular.module('controllers', [])
	.controller('LoginController', ['$scope', '$rootScope', '$http', '$location', function($scope, $rootScope, $http, $location) {
		this.login = function() {
			$http.get(`/teamforce/worker/login?user=${this.username}&passwd=${this.password}`)
				.then(function(response) {
					$rootScope.user = response;
					$rootScope.hideMessage = true;
					$location.path('/teamforce/board', false);
				})
				.catch(function (error) {
					console.error(error);
					$rootScope.hideMessage = false;
					$rootScope.message = "You haven't the force...";
				});
		};
	}])
	.controller('UserController', ['$scope', '$rootScope', '$http', '$location', function($scope, $rootScope, $http, $location) {
			this.save = function() {
				if (_s.anyBlanks([this.id, this.password, this.name, this.email])) {
					$rootScope.hideMessage = false;
					$rootScope.message = "I think lost anything...";
					return;					
				}
				const data = JSON.stringify({
					id: this.id,
					password: this.password,
					name: this.name,
					email: this.email,
				});
				$http.post(`/teamforce/worker`, data)
					.then(function(response) {
						$rootScope.user = response;
						$rootScope.hideMessage = true;
						$location.path('/teamforce/login', false);
					})
					.catch(function (error) {
						console.error(error);
						$rootScope.hideMessage = false;
						$rootScope.message = "Anything wrong...";
					});
			};
		}]);