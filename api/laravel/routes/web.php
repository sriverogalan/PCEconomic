<?php

use App\Http\Controllers\ArticlesController;
use App\Http\Controllers\CategoriaController;
use App\Http\Controllers\SubcategoriaController;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\MarquesController;
use App\Http\Controllers\PersonesController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return response()->json(true);
});

Route::controller(MarquesController::class)->group(function () {
    Route::get('/api/get/marques', 'index');
    Route::post('/api/create/marques', 'create');
    Route::post('/api/update/marques', 'update');
    Route::post('/api/delete/marques', 'delete');
    // TODO: Hacer papelera para recuperar eliminados
});

Route::controller(ArticlesController::class)->group(function () {
    Route::get('/api/get/articles', 'index');
    Route::post('/api/create/articles', 'create');
    Route::post('/api/update/articles', 'create');
    Route::post('/api/delete/articles', 'destroy');
});

Route::controller(PersonesController::class)->group(function () {
    Route::get('/api/get/persones', 'index');
    Route::get('/api/get/rols', 'getRols');
    Route::post('api/roles/edit', 'editRols');
    Route::post('/api/create/persones', 'create');
    Route::post('/api/update/persones', 'update');
    Route::post('/api/delete/persones', 'destroy');
    Route::post('/api/roles/admin/add', 'addPersonAsAdmin');
});

Route::controller(CategoriaController::class)->group(function () {
    Route::get('/api/get/categories', 'index');
    Route::post('/api/create/categories', 'store');
    Route::post('/api/update/categories', 'update');
    Route::post('/api/delete/categories', 'destroy');
});

Route::controller(SubcategoriaController::class)->group(function () {
    Route::get('/api/get/subcategories', 'index');
    Route::post('/api/create/subcategories', 'store');
    Route::post('/api/update/subcategories', 'update');
    Route::post('/api/delete/subcategories', 'destroy');
});
