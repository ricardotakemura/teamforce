angular.module("controllers", [])
	.controller("LoginController", ["$scope", "$rootScope", "$http", "$location", function($scope, $rootScope, $http, $location) {
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
					$location.path("/teamforce/board", false);
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("You haven't the force...");
				});
		};

		this.init();
	}])
	.controller("UserController", ["$scope", "$rootScope", "$http", "$location", function($scope, $rootScope, $http, $location) {
		this.init = function() {
			$rootScope.hideMessage = true;
			$rootScope.user = null;
		};

		this.save = function() {
			if (_s.anyBlanks([this.id, this.password, this.name, this.email])) {
				$rootScope.showMessage(`I think missing anything...`);
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
					$location.path("/teamforce/login", false);
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("Anything wrong...");
				});
		};

		this.init();
	}])
	.controller("BoardController", ["$scope", "$rootScope", "$http", "$location", function($scope, $rootScope, $http, $location) {
		this.init = function() {
			$rootScope.hideMessage = true;
			$scope.showAddTask = false;
			$scope.showTaskUser = false;
			$scope.tasks = [];
			$scope.workers = [];
			$http.get("/teamforce/task")
				.then(function(response) {
					$scope.tasks = response.data;
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("Not loading, tasks are...");
				});
			$http.get("/teamforce/worker")
				.then(function(response) {
					$scope.workers = response.data;
				})
				.catch(function(error) {
					$rootScope.showMessage("Not loading, workers are...");
					console.error(error);
				});
		};
		
		this.chooseWorker = function(taskId) {
			$scope.showTaskUser = true;
			$scope.taskId = taskId;
		}
	
		this.addTask = function() {
			if (_s.anyBlanks([this.type, this.description])) {
				$rootScope.showMessage("I think missing anything...");
				return;
			}
			if (this.description.length < 10) {
				$rootScope.showMessage("Short, the description is...");
				return;
			}
			if (this.description.length > 100) {
				$rootScope.showMessage("Large, the description is...");
				return;
			}
			const data = JSON.stringify({
				type: this.type,
				description: this.description,
			});
			$http.post("/teamforce/task", data)
				.then(function(response) {
					const task = response.data;
					$scope.tasks.push(task);
					$scope.showAddTask = false;
					$rootScope.showMessage("Task force created...");
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("No task force created...");
				});
		};

		this.saveTaskStatus = function() {
			if (_s.anyBlanks([this.status, this.task])) {
				$rootScope.showMessage("I think missing anything...");
				return;				
			}
			$http.patch(`/teamforce/task/${this.task}/status/${this.status}`)
				.then(function() {
					$rootScope.showMessage("Welcome to the next step!");
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("The task don't to the next step...");
				});
		};

		this.saveWorkerInTask = function() {
			if (_s.anyBlanks([this.workerId, $scope.taskId])) {
				$rootScope.showMessage("I think missing anything...");
				return;				
			}
			$http.patch(`/teamforce/task/${$scope.taskId}/worker/${this.workerId}`)
				.then(function(response) {
					const updateTask = response.data;
					const index = $scope.tasks
						.findIndex(function({id}) {
							return id == updateTask.id;
						});
					if (index > -1) {
						$scope.tasks[index] = updateTask;
					}
					$scope.showTaskUser = false;
					$rootScope.showMessage("Jedi get a task...");
				})
				.catch(function(error) {
					console.error(error);
					$rootScope.showMessage("Jedi don't get task...");
				});
		}

		this.init();
	}]);
