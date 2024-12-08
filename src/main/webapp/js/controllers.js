angular.module('controllers', [])
	.controller('LoginController', ['$scope', '$rootScope', '$http', '$location', function($scope, $rootScope, $http, $location) {
		this.init = function() {
			$rootScope.hideMessage = true;
			$rootScope.user = null;
		};

		this.login = function() {
			if (_s.anyBlanks([this.username, this.password])) {
				$rootScope.showMessage("I think missing anything...");
				return;
			}
			$http.get(`/teamforce/worker/login?user=${this.username}&passwd=${this.password}`)
				.then(function(response) {
					$rootScope.user = response.data;
					$rootScope.hideMessage = true;
					$location.path('/teamforce/board', false);
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("You haven't the force...");
				});
		};

		this.init();
	}])
	.controller('UserController', ['$scope', '$rootScope', '$http', '$location', function($scope, $rootScope, $http, $location) {
		this.init = function() {
			$rootScope.hideMessage = true;
			$rootScope.user = null;
		};

		this.save = function() {
			if (_s.anyBlanks([this.id, this.password, this.name, this.email])) {
				$rootScope.showMessage("I think missing anything...");
				return;
			}
			if (this.id.length < 4) {
				$rootScope.showMessage("Short, the id is...");
				return;
			}
			if (_s.noEmail(this.email)) {
				$rootScope.showMessage("Wrong, the email is...");
				return;
			}
			if (this.password.length < 4) {
				$rootScope.showMessage("Short, the password is...");
				return;
			}
			const data = JSON.stringify({
				id: this.id,
				password: this.password,
				name: this.name,
				email: this.email,
			});
			$http.post(`/teamforce/worker`, data)
				.then(function() {
					$rootScope.hideMessage = true;
					$location.path('/teamforce/login', false);
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("Anything wrong...");
				});
		};

		this.init();
	}])
	.controller('BoardController', ['$scope', '$rootScope', '$http', '$location', function($scope, $rootScope, $http, $location) {
		this.init = function() {
			$rootScope.hideMessage = true;
			this.showAddTask = false;
			this.showTaskUser = false;
		};
	
		this.addTask = function() {
			if (_s.anyBlanks([this.type, this.description])) {
				$rootScope.showMessage("I think missing anything...");
				return;
			}
			if (this.description.length < 10) {
				$rootScope.showMessage("Short, the description is...");
				return;
			}
			const data = JSON.stringify({
				type: this.type,
				description: this.description,
			});
			const self = this;
			$http.post(`/teamforce/task`, data)
				.then(function(response) {
					$rootScope.showMessage("Task force created...");
					self.showAddTask = false;
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("No task force created...");
				});
		};
		
		this.saveUserInTask = function() {
			if (_s.isBlank(this.user)) {
				$rootScope.showMessage("I think missing anything...");
				return;				
			}
			this.showTaskUser = false;
			/*
			$http.get(`/teamforce/worker/login?user=${this.username}&passwd=${this.password}`)
				.then(function(response) {
					$rootScope.user = response.data;
					$rootScope.hideMessage = true;
					$location.path('/teamforce/board', false);
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("You haven't the force...");
				});
			*/
		}

		this.init();
	}]);
