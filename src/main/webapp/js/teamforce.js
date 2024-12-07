angular.module('teamForceApp', ['ngRoute', 'components', 'controllers'])
	.config(['$routeProvider',
    	function config($routeProvider) {
      		$routeProvider.
        		when('/teamforce/board', {
         	 		template: '<board></board>',
					auth: true,
        		}).
        		when('/teamforce/newuser', {
          			template: '<newuser></newuser>'
        		}).
				when('/teamforce/login', {
					template: '<login></login>'
				}).
        		otherwise('/teamforce/login');
   		}
  	])
	.run(function($location, $rootScope, $route) {
		$rootScope.hideMessage = true;
		$rootScope.$on('$locationChangeStart', function() {
			const nextPath = $location.path();
			const { auth = false } = $route.routes[nextPath] || {};
			const logged = !!$rootScope.user;
			if (auth && !logged) {
				$location.path('/teamforce/login');
			}
		});
	});