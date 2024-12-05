angular.module('teamForceApp', ['components'])
	.controller('LoginController', function ($scope, $http) {
		$scope.login = function(user, passwd) {
			$http.get(`/teamforce/worker/login?user=${user}&passwd=${passwd}`)
				.then(function(response) {
					console.log(JSON.stringify(response));
				})
				.catch(function (error) {
					console.error(JSON.stringify(error));
				});
		}
	});