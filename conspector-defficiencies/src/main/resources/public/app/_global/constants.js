'use strict';

var appSettings = {
    apiUrl: {
        defaultRedirect: 'http://www.tasconline.com',
        authentication: '/cim/authenticate'
    },

    errorMessages: {
        authenticationFailed: 'Invalid username and/or password.',
        emptyUsername: 'Username is mandatory',
        emptyPassword: 'Password is mandatory'
    }
};

module.exports = appSettings;