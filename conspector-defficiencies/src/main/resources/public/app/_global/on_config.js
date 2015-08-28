'use strict';

function OnConfig($urlRouterProvider, $httpProvider) {
    $urlRouterProvider.otherwise('deficiencies-list');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest'; //to avoid authorization  popup (401 responce instead)

    $httpProvider.interceptors.push(function($injector, $window, $cookies, $q) {
        var canceller = $q.defer();
        return {
            'responseError': function(err, status) {
                switch (err.status) {
                    case 401:
                        $window.location.href = "http://localhost:8080/#/login?location=" + encodeURIComponent($window.location.href); // TODO: should be dynamic
                        break;
                }

                return $q.reject(err);
            }
        };
    });
}

module.exports = OnConfig;