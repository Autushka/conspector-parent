'use strict';

var gulp = require('gulp');
var runSequence = require('run-sequence');

gulp.task('dev', ['lib'], function(cb) {
    global.isProd = false;
    // cb = cb || function() {};
    cb = function() {
        console.log('\n');
        console.log('-----------------------------------------------');
        console.log('gulp dev task finished and is in watch mode now');
        console.log('use http://localhost:9000 to access the app');
        console.log('-----------------------------------------------');
        console.log('\n');
    };

    // runSequence(['styles', 'views', 'index', 'browserify'], 'clean', 'watch', cb);
    runSequence(['styles', 'views', 'browserify'], 'clean', 'watch', cb);
});