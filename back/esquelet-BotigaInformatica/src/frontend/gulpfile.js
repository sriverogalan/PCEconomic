const { watch, src, dest, series, task } = require("gulp");
const sass = require("gulp-sass")(require("sass"));

async function defaultTask() {
  return "default task";
}

async function compileSASS() {
  return src("../main/resources/static/sass/*.scss")
    .pipe(sass.sync().on("error", sass.logError))
    .pipe(dest("../main/resources/static/css"));
}

task("watch", async function () {
  watch("./scss/**/*.scss", { usePolling: true }, series(compileSASS));
});

function copyjs() {
  return src("./node_modules/bootstrap/dist/js/*.js").pipe(dest("./js"));
}

exports.jscopy = copyjs;
exports.compile = compileSASS;
exports.default = compileSASS;
