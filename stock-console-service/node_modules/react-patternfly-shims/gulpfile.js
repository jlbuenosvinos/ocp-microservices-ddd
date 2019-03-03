var gulp = require('gulp'),
  $ = require('gulp-load-plugins')();


gulp.task('js', function () {
  return gulp.src(['src/**/*.js'])
    .pipe($.plumber())
    .pipe($.babel(
      {
        presets: ['es2015','react','stage-2']
      }
    ))
    .pipe(gulp.dest('lib/'));
});

gulp.task('build', ['js']);