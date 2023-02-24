const {watch, src, dest, series, task} = require("gulp");
const sass = require("gulp-sass")(require("sass"));
const cleanCss = require('gulp-clean-css');

async function defaultTask() {
    return "default task";
}

async function compileSASS() {
    return src("../main/resources/static/sass/*.scss")
        .pipe(sass.sync().on("error", sass.logError))
        .pipe(cleanCss())
        .pipe(dest("../main/resources/static/css"));
}

task("watch", async function () {
    watch("./scss/**/*.scss", {usePolling: true}, series(compileSASS));
});

function jscopy() {
    return src("./node_modules/bootstrap/dist/js/*.js").pipe(dest("../main/resources/static/js/"));
}

exports.jscopy = jscopy;
exports.compileSASS = compileSASS;
exports.default = series(compileSASS, jscopy);
exports.build = series(compileSASS, jscopy);