'use strict';

describe('LoginCtrl', function() {
    var ctrl, scope, httpBackend, cookies, APP_SETTINGS, windowMock;

    beforeEach(module('app'));

    beforeEach(inject(function($controller, $rootScope, _APP_SETTINGS_) {
        scope = $rootScope.$new();
        APP_SETTINGS = _APP_SETTINGS_;
        windowMock = {
            location: {
                href: ""
            }
        };

        ctrl = $controller('LoginCtrl', {
            $scope: scope,
            $window: windowMock
        });
    }));
});